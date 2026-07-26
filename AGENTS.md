# fast-web 项目 AI Agent 规则
# 放置于项目根目录，供各 AI 编码工具（Claude Code / OMP / Codex / Gemini 等）原生读取。

## 项目定位
- Spring Boot Web 脚手架（JDK 21+，Maven / Gradle 双构建，包根 `com.lv.fast`）。
- 目标：开箱即用的 Java Web 脚手架，避免重复配置日志 / ORM / 异常。

## 通用
- 用户使用中文时优先用中文回复。
- 遵循既有目录与命名约定，不要引入第二套并行约定。
- 复杂逻辑必须加注释；变量命名要有意义。

## 代码约定
- 业务代码一律放在 `com.lv.fast.module.<模块名>` 下，按模块划分包。
- MVC 分层：`controller` / `service` / `dao` / `dto` / `enums`。
- 统一响应：返回 `com.lv.fast.common.entity.RestResult`，用 `RestResult.success(...)` / `RestResult.error()`。
- 业务异常：抛 `com.lv.fast.exception.BusinessException`，由 `GlobalExceptionHandler` 统一捕获。
- 参数校验：使用 `jakarta.validation` + 自定义注解（`EnumCheck` / `Phone`），分组用 `AddStrategy` / `UpdateStrategy`。
- 分页：入参继承 `com.lv.fast.common.entity.PageQuery`，用 `com.lv.fast.common.util.PageUtil`。
- MyBatis-Plus mapper 扫描 `com.*.*.dao`，实体别名 `com.**.DO`，XML 放 `src/main/resources/mapper`。

## 禁止
- 不要在 controller 里直接写业务逻辑，业务下沉到 service。
- 不要绕过 `RestResult` 自定义响应结构。
- 不要手写 SQL 拼接，使用 MyBatis-Plus / 占位符防注入。

## AI 开发工作流（spec-superflow）
- 本项目集成 [spec-superflow](https://github.com/MageByte-Zero/spec-superflow) Spec-first 开发流程。
- 大型功能开发必须走工作流：`/skill:workflow-start` 或说「用 workflow-start 开始」。
- 流程：需求澄清 → 工件沉淀（proposal/spec/design/tasks）→ 执行契约 → TDD + Review Gate → 验证收口。
- 小型变更（≤4 文件纯配置/文档）走 tweak 模式，直接编辑。
- 工件模板位于 `.ai/templates/`，phase-guard 规则仅在 spec-superflow 上下文时自动激活。
- CLI 工具：`npx spec-superflow@latest <command>`（list / validate / doctor / execution 等）。
