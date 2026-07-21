---
description: Git 工作规范（commit / 分支 / 合并等）
alwaysApply: true
---

# Git 规范

## Commit Message

生成 commit message 时必须遵守 Angular 规范：

```
<type>(<scope>): <subject>

[body]

[footer]
```

## type 取值

| type | 说明 |
|------|------|
| `feat` | 新功能 |
| `fix` | 修复 bug |
| `docs` | 文档变更 |
| `style` | 代码格式（不影响逻辑） |
| `refactor` | 重构（非新功能、非修复） |
| `perf` | 性能优化 |
| `test` | 测试相关 |
| `build` | 构建/依赖变更 |
| `ci` | CI 配置变更 |
| `chore` | 杂项（不修改 src/test） |
| `revert` | 回滚 |

## 规则

- **scope**：影响范围（模块名或文件名），可省略
- **subject**：祈使句，首字母小写，不加句号，≤50 字符
- **body**：说明 what 和 why，每行 ≤72 字符，可省略
- **footer**：Breaking Change 以 `BREAKING CHANGE:` 开头；关联 issue 用 `Closes #<number>`
- **范围**：只提交与本次会话相关的内容，禁止提交其他会话的变更及无关内容
- **语言**：subject、body、footer 必须使用中文（type 和 scope 除外）

## 示例

```
feat(module/user): 新增用户注册接口

实现用户注册功能，支持邮箱验证。
使用 RestResult 统一响应格式。

Closes #42
```

```
fix(redis): 修复缓存清除竞态条件

BREAKING CHANGE: RedisBatchEvict 现在要求显式传入 key 列表。
```

```
docs: 更新 README 增加 AI 工作流章节
```
