#!/usr/bin/env bash
# fast-web 项目级 AI 统一配置管理脚本
# 用法: ./.ai/ai-manage.sh {sync|status|list}
set -euo pipefail

AI_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
ROOT_DIR="$(dirname "$AI_DIR")"
SKILLS_DIR="$AI_DIR/skills"
RULES_DIR="$AI_DIR/rules"
HOOKS_DIR="$AI_DIR/hooks"
OMP_DIR="$ROOT_DIR/.omp"

log()  { echo "[ai-manage] $*"; }
warn() { echo "[ai-manage][WARN] $*"; }

safe_symlink() {
    local target="$1" link="$2"
    if [ -L "$link" ]; then
        local current
        current=$(readlink "$link")
        if [ "$current" = "$target" ]; then log "OK: $link"; return 0; fi
        rm -f "$link"
    elif [ -e "$link" ]; then
        warn "Backing up existing $link -> ${link}.bak"
        mv "$link" "${link}.bak"
    fi
    ln -s "$target" "$link"
    log "Linked: $link -> $target"
}

sync_links() {
    mkdir -p "$OMP_DIR"
    safe_symlink "../.ai/skills" "$OMP_DIR/skills"
    safe_symlink "../.ai/rules" "$OMP_DIR/rules"
    safe_symlink "../.ai/hooks" "$OMP_DIR/hooks"
    log "OMP synced"

    # 验证 skill 链接完整性
    local ok=0 fail=0
    for link in "$SKILLS_DIR"/*/; do
        [ -L "${link%/}" ] || continue
        if [ -f "$link/SKILL.md" ]; then ok=$((ok + 1)); else fail=$((fail + 1)); warn "Broken: $link"; fi
    done
    log "Skills: $ok OK, $fail broken"
    log "All symlinks synced!"
}

list_skills() {
    log "Skills in $SKILLS_DIR:"
    for item in "$SKILLS_DIR"/*; do
        [ -e "$item" ] || continue
        local name
        name=$(basename "$item")
        if [ -L "$item" ]; then
            echo "  [LINK] $name -> $(readlink "$item")"
        elif [ -d "$item" ]; then
            echo "  [DIR]  $name/"
        fi
    done
}

status() {
    echo "=== fast-web AI Config Status ==="
    for link in "$OMP_DIR/skills" "$OMP_DIR/rules" "$OMP_DIR/hooks"; do
        if [ -L "$link" ]; then echo "  [OK] $link -> $(readlink "$link")"
        elif [ -e "$link" ]; then echo "  [!!] $link (not symlink)"
        else echo "  [--] $link (missing)"; fi
    done
    echo ""
    list_skills
    echo ""
    # 检查上游是否存在
    if [ -d "$ROOT_DIR/.spec-superflow/skills" ]; then
        echo "  [OK] .spec-superflow/ present"
    else
        echo "  [!!] .spec-superflow/ missing — run: git submodule update --init"
    fi
}

case "${1:-help}" in
    sync) sync_links ;;
    list) list_skills ;;
    status) status ;;
    *) echo "Usage: $0 {sync|list|status}" ;;
esac
