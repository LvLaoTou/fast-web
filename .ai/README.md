# ./.ai — fast-web 项目统一 AI 配置中心

> 所有 AI 编码工具（OMP 等）通过 `.omp/` 软链接共享本目录下的 skills、rules、hooks。
> 上游工作流引擎位于 `.spec-superflow/`（git submodule），`.ai/skills/` 内通过软链接引用。

## 目录结构

```
.ai/
├── skills/                       # OMP 发现入口（9 条软链接 → .spec-superflow/skills/）
│   ├── workflow-start -> ../../.spec-superflow/skills/workflow-start
│   ├── need-explorer -> ...
│   ├── spec-writer -> ...
│   ├── contract-builder -> ...
│   ├── build-executor -> ...
│   ├── bug-investigator -> ...
│   ├── code-reviewer -> ...
│   ├── release-archivist -> ...
│   └── spec-merger -> ...
├── rules/
│   └── spec-superflow.md         # phase-guard（TTSR 条件触发）
├── hooks/
│   └── spec-superflow.ts         # session_start 上下文注入
├── ai-manage.sh                  # 管理脚本（sync / status / list）
└── README.md
```

上游（`.spec-superflow/`，.gitignore 忽略）：
```
.spec-superflow/
├── skills/                       # 9 个 workflow skill 源文件
├── scripts/                      # ssf CLI 源码
├── templates/                    # 工件模板（proposal/spec/design/tasks 等）
├── docs/                         # 状态机/决策点文档
└── ...
```

## 软链接映射

| 工具 | 软链接 | 指向 |
|------|--------|------|
| OMP | `.omp/skills` | `.ai/skills` |
| OMP | `.omp/rules` | `.ai/rules` |
| OMP | `.omp/hooks` | `.ai/hooks` |

> `.ai/`、`.omp/`、`.spec-superflow/` 均纳入版本管理，团队 clone 后即可使用。

## 初始化（clone 后）

```bash
# 软链接已入库，通常无需操作。若链接异常：
./.ai/ai-manage.sh sync
```

## 管理命令

```bash
./.ai/ai-manage.sh sync      # 建立/修复 .omp/ 软链接 + 验证 skill 完整性
./.ai/ai-manage.sh status    # 查看状态
./.ai/ai-manage.sh list      # 列出已安装 skill
```

## spec-superflow 工作流

来源：[MageByte-Zero/spec-superflow](https://github.com/MageByte-Zero/spec-superflow)（v0.10.0）

- 启动：`/skill:workflow-start` 或说「用 workflow-start 开始」
- CLI：`npx spec-superflow@latest <command>`（list / validate / doctor / execution 等）
- 工件模板：`.spec-superflow/templates/`
- 状态机文档：`.spec-superflow/docs/state-machine.md`

## 升级上游

```bash
cd .spec-superflow && git pull origin main && cd ..
# 或重新 clone：
rm -rf .spec-superflow
git clone --depth 1 https://github.com/MageByte-Zero/spec-superflow.git .spec-superflow
# .ai/skills/ 内的软链接指向不变，无需重建
```

## Agent 注意事项

- **不要破坏软链接**：始终在 `.ai/` 内改配置，不要修改 `.spec-superflow/` 内的文件。
- **phase-guard 规则**仅在检测到 spec-superflow 上下文时触发（TTSR condition），日常编码不受干扰。
- **Skill 是只读说明文档**，告诉 agent 如何执行工作流；实际约定以 `src/` 代码为准。
