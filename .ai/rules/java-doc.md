---
description: Java 文件 Doc 注释规范（@author 取值与 AI 生成标注）
alwaysApply: true
---

# Java Doc 注释规范

## 适用范围

所有新建的 `.java` 文件（class / interface / enum / record / annotation）必须包含类级 Javadoc。
修改已有文件时，**不得**篡改原有 `@author`，仅可追加。

## 类级 Javadoc 模板

```java
/**
 * <一句话描述该类职责>
 *
 * <p>补充说明（可选）</p>
 *
 * @author <作者>
 * @since <日期 YYYY-MM-DD>
 * @generated-by <agent工具> (<大模型>)
 */
```

## @author 取值优先级

按以下顺序取**第一个可用值**，不可跳级：

| 优先级 | 来源 | 说明 |
|--------|------|------|
| 1 | 用户指定 | 用户在对话中明确给出的作者名/署名 |
| 2 | 系统用户名 | `System.getProperty("user.name")` 或 `$USER` / `$USERNAME` |
| 3 | Git 用户 | `git config user.name` 的输出 |
| 4 | Agent 工具名 | 当前 agent 工具标识（如 `oh-my-pi`） |

> 规则：优先级 1-3 是**人类作者**；仅当三者均不可获取时，才退化为优先级 4。
> 无论 `@author` 取何值，`@generated-by` 行**始终必须存在**。

## @generated-by 格式

```
@generated-by <agent工具名> (<大模型标识>)
```

- `<agent工具名>`：当前编码工具的产品名，如 `oh-my-pi`、`claude-code`、`cursor`、`copilot`。
- `<大模型标识>`：实际生成代码的模型 ID，如 `qwen3.8-max-preview`、`claude-sonnet-4-20250514`。
- 两者之间用空格分隔，模型标识用英文括号包裹。

## 完整示例

```java
/**
 * 用户注册服务
 *
 * <p>封装注册流程中的邮箱验证、密码加密与持久化逻辑。</p>
 *
 * @author lvlaotou
 * @since 2026-07-23
 * @generated-by oh-my-pi (qwen3.8-max-preview)
 */
@Service
public class UserRegisterService {
    // ...
}
```

当用户未指定、系统用户名和 git user 均不可用时：

```java
/**
 * 临时工具类
 *
 * @author oh-my-pi
 * @since 2026-07-23
 * @generated-by oh-my-pi (qwen3.8-max-preview)
 */
```

## 禁止事项

- **禁止**省略 `@generated-by`（只要是 AI 生成的代码）。
- **禁止**将大模型名写入 `@author`（`@author` 优先填人类）。
- **禁止**伪造或猜测用户姓名——取不到就按优先级降级。
- **禁止**在已有文件的 `@author` 上覆盖原值，只能追加新行。
