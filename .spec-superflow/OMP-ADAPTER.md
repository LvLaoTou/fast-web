# OMP 适配说明

> 本文档描述 spec-superflow 在 [OMP (Oh My Pi)](https://omp.sh) 上的适配细节。
> 上游支持 18 个平台（见 `docs/platform-matrix.md`），但 **不包含 OMP**。
> 本文件记录项目级适配方案，使 spec-superflow 在 OMP 中完整可用。

---

## 与上游平台支持的差异

| 对比项 | 上游（Claude Code 等） | OMP 适配 |
|--------|----------------------|----------|
| 安装方式 | `/plugin install`、`install-<platform>` 脚本 | 手动软链接（`.ai/ai-manage.sh sync`） |
| Skills 发现 | 平台原生扫描 plugin root | `.omp/skills/` 一层非递归扫描 `*/SKILL.md` |
| Rules 守卫 | phase-guard 规则文件部署到平台 rules 目录 | `.omp/rules/` 多文件目录，支持 TTSR 条件触发 |
| SessionStart Hook | `hooks/hooks.json`（Claude 格式，bash 命令） | `.omp/hooks/*.ts`（OMP 原生 JS/TS 事件模块） |
| CLI 调用 | `npx --yes --package spec-superflow@x.y.z ssf ...` | 相同，走 OMP bash 工具 |

> 上游的 `install-pi` 脚本部署到 `.pi/skills/`，但 **Pi ≠ OMP**。
> OMP 虽源自 Pi 生态，但 skill 发现路径、hook 格式、rules 系统均不同。

---

## 适配架构

```
fast-web/
├── .ai/                              # 项目统一配置中心（入库）
│   ├── skills/                       # 9 条软链接 → .spec-superflow/skills/<name>
│   │   ├── workflow-start -> ../../.spec-superflow/skills/workflow-start
│   │   ├── need-explorer -> ...
│   │   └── ...
│   ├── rules/
│   │   └── spec-superflow.md         # phase-guard（TTSR 条件触发）
│   └── hooks/
│       └── spec-superflow.ts         # session_start 事件注入
├── .spec-superflow/                  # 上游源文件（入库）
│   ├── skills/                       # 9 个 skill 源文件
│   ├── scripts/                      # ssf CLI
│   ├── templates/                    # 工件模板
│   └── docs/                         # 状态机/决策点
└── .omp/                             # OMP 发现层（入库）
    ├── skills -> ../.ai/skills       # 顶层目录软链接
    ├── rules -> ../.ai/rules
    └── hooks -> ../.ai/hooks
```

---

## Skills 适配

### OMP 发现机制

OMP 的 native provider 扫描 `.omp/skills/` **一层**（非递归）：

```
.omp/skills/<entry>/SKILL.md   → 发现
.omp/skills/<entry>/sub/SKILL.md → 不发现
```

扫描时 `stat()` 默认跟随 symlink，因此 symlink 目录与真实目录等效。

### 软链接链路

```
OMP 扫描 .omp/skills/
  │  (顶层 symlink → .ai/skills/)
  ▼
readdir(.ai/skills/)
  │
  ├─ workflow-start (symlink → .spec-superflow/skills/workflow-start)
  │    └─ stat("workflow-start/SKILL.md") → 跟随到真实文件 → ✅ 命中
  │
  ├─ need-explorer (symlink → ...) → ✅ 命中
  └─ ...（共 9 条）
```

### 为什么不用 `.omp/skills -> .spec-superflow/skills` 直接链接？

直接链接可行，但：
1. 无法在 `.ai/skills/` 内混合其他来源的 skill（如项目专属 skill）
2. 归属不清晰——看不出哪些 skill 属于 spec-superflow
3. 升级上游时若 skill 列表变动，需要逐条管理

当前方案通过 `.ai/skills/` 中间层实现：
- 归属明确（所有链接指向 `.spec-superflow/skills/`）
- 可扩展（未来在 `.ai/skills/` 内添加项目专属 skill 目录即可）
- 升级时只需更新 `.spec-superflow/`，链接自动生效

### Skill 格式兼容性

上游 skill 格式：
```yaml
---
name: workflow-start
description: Primary entry point for the spec-superflow state-machine workflow...
---
# Workflow Start
...
```

OMP 要求：
- 每个 skill 独占一个目录：`<name>/SKILL.md` ✅
- frontmatter 含 `name` 和 `description` ✅（native provider 要求 `description` 必填）
- 内容通过 `skill://<name>` 按需读取 ✅

**完全兼容，无需修改上游 skill 文件。**

---

## Rules 适配

### 上游机制

上游通过安装器将 phase-guard 规则部署到平台 rules 目录（如 `.cursor/rules/`、`.clinerules/`），平台自动加载为常驻上下文。

### OMP 机制

OMP 支持多文件 rules 目录（`.omp/rules/*.{md,mdc}`），并额外支持 **TTSR（Time Traveling Stream Rules）**：

| 模式 | frontmatter | 行为 |
|------|-------------|------|
| Always-apply | `alwaysApply: true` | 全文注入系统提示，始终生效 |
| Rulebook | `description: ...`（无 condition） | 仅列出 name+description，按需 `rule://` 读取 |
| TTSR | `condition: "<regex>"` | 仅在流式输出匹配正则时自动注入，日常不干扰 |

### 本项目选择：TTSR 条件触发

```yaml
---
description: spec-superflow phase guard — 防止阶段漂移和未授权实现
condition: "(?i)(spec-superflow|workflow-start|execution-contract|\\.spec-superflow\\.yaml|need-explorer|spec-writer|contract-builder|build-executor)"
---
```

效果：
- 日常编码（不提及 spec-superflow 相关词汇）→ 规则**不注入**，零干扰
- 提到 workflow-start / execution-contract 等 → 规则**自动注入**，强制阶段纪律

这比上游的 always-apply 更精准——上游在所有支持 rules 的平台上都是常驻加载。

---

## Hooks 适配

### 上游格式（不兼容 OMP）

```json
// hooks/hooks.json — Claude Code 格式
{
  "hooks": {
    "SessionStart": [{
      "hooks": [{ "type": "command", "command": "bash \"${CLAUDE_PLUGIN_ROOT}/hooks/session-start\"" }]
    }]
  }
}
```

### OMP 格式

OMP hooks 是 `.omp/hooks/*.ts` 的 JS/TS 模块，default-export 一个工厂函数：

```ts
import type { HookAPI } from "@oh-my-pi/pi-coding-agent/extensibility/hooks";

export default function (pi: HookAPI): void {
  pi.on("session_start", async () => {
    pi.sendMessage({
      role: "custom",
      customType: "spec-superflow-context",
      content: [{ type: "text", text: "spec-superflow 工作流已加载。使用 /skill:workflow-start 启动。" }],
    });
  });
}
```

### 差异总结

| 对比 | 上游 hooks.json | OMP hooks |
|------|----------------|-----------|
| 格式 | JSON + bash 命令 | TypeScript 事件模块 |
| 事件名 | `SessionStart` | `session_start` |
| 注入方式 | 执行 shell 脚本输出文本 | `pi.sendMessage()` API |
| 发现路径 | plugin root `hooks/` | `.omp/hooks/*.ts` |
| 能力 | 仅注入文本 | 可注册命令、拦截工具调用、修改上下文等 |

---

## CLI 兼容性

所有 `ssf` 命令通过 `npx --yes --package spec-superflow@0.10.0 ssf <cmd>` 调用，走 OMP 的 bash 工具执行。

- 无平台依赖，完全兼容
- 首次调用有 ~2s 网络延迟（npx 拉包），后续走缓存
- 离线环境可预装：`npm i -g spec-superflow`

---

## 升级上游

```bash
cd .spec-superflow && git pull origin main && cd ..
# 或重新 clone：
rm -rf .spec-superflow
git clone --depth 1 https://github.com/MageByte-Zero/spec-superflow.git .spec-superflow
```

`.ai/skills/` 内的 9 条软链接指向 `.spec-superflow/skills/<name>`，上游 skill 目录名不变则无需重建。
若上游新增/重命名 skill，需手动更新 `.ai/skills/` 内的链接。

---

## 已知限制

1. **无自动更新**：上游通过 marketplace 推送更新，本项目需手动 `git pull`。
2. **Hook 能力未充分利用**：当前仅用 `session_start` 注入提示。OMP 支持 `tool_call` 拦截（可替代 phase-guard 做硬守卫），未来可增强。
3. **TTSR 正则维护**：若上游新增 skill 名称，需更新 `condition` 正则。
4. **`ssf inject` 平台标记**：`ssf inject <dir> --platforms` 无 `omp` 选项，使用 `--platforms all` 或省略（单平台时自动检测）。
