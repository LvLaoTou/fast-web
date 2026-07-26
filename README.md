# fast-web — AI 协作开发脚手架

快速启动一个**可团队协作使用 AI 工具开发**的 Spring Boot Web 项目。

开箱即用的不仅是日志、ORM、异常等基础配置，更是一套完整的 AI 辅助开发工作流——让团队成员（无论使用 OMP、Claude Code、Codex 还是其他 AI 编码工具）clone 后即可在统一的 Spec-first 流程下协作开发。

## 核心理念

```
传统脚手架：clone → 配置 → 写代码
fast-web：  clone → AI 工具自动就绪 → Spec-first 工作流驱动开发
```

- **AI 配置即代码**：`.ai/`、`.omp/`、`AGENTS.md` 全部入库，团队共享，零配置上手
- **工作流纪律**：大型功能必须走 Spec-first 流程（需求澄清 → 设计 → 契约 → TDD + Review），防止 AI 跑偏
- **工具无关**：统一配置架构兼容多种 AI 编码工具，不绑定单一平台
- **日常零干扰**：phase-guard 规则仅在 AI 工作流上下文时激活，普通编码不受影响

## 项目结构

```
|--fast-web
    |-- .ai/                          AI 统一配置中心
        |-- skills/                   工作流技能（软链接 → .spec-superflow/skills/）
        |-- rules/                    项目规则（phase-guard，TTSR 条件触发）
        |-- hooks/                    OMP 事件钩子（session_start 注入）
        |-- ai-manage.sh             管理脚本（sync / status / list）
        |-- README.md                配置详细说明
    |-- .spec-superflow/             spec-superflow 工作流引擎（上游）
        |-- skills/                  9 个 workflow skill 源文件
        |-- scripts/                 ssf CLI 源码
        |-- templates/               工件模板（proposal / spec / design / tasks 等）
        |-- docs/                    状态机 / 决策点文档
        |-- OMP-ADAPTER.md           OMP 适配细节说明
    |-- .omp/                        OMP 发现层（软链接 → .ai/）
    |-- AGENTS.md                    AI Agent 项目规则（各工具原生读取）
    |-- src/
        |-- main/
            |-- java/com/lv/fast/
                |-- common/          公共能力（aop / constant / entity / enums / log / util / valid）
                |-- config/          配置（MybatisPlus / WebMvc）
                |-- exception/       异常（BusinessException / GlobalExceptionHandler）
                |-- module/          业务模块（按模块划分，test 为示例）
                |-- redis/           Redis 缓存注解（可选，不需要可删除）
            |-- resources/
                |-- mapper/          MyBatis-Plus XML
                |-- application.yml
                |-- application-dev.yml
                |-- log4j2-spring.xml
        |-- test/                    单元测试
    |-- pom.xml                      Maven 构建
    |-- build.gradle                 Gradle 构建（与 pom.xml 依赖对齐）
    |-- settings.gradle
    |-- gradlew / gradlew.bat        Gradle Wrapper
    |-- Dockerfile
    |-- LICENSE
    |-- TODO.md
```

## 快速开始

### 1. Clone 即用

```bash
git clone <repo-url> && cd fast-web
```

AI 配置已全部入库，无需额外初始化。若软链接异常：
```bash
./.ai/ai-manage.sh sync
```

### 2. 构建与运行

项目同时支持 Maven 和 Gradle，选择其一即可：

```bash
# Maven
mvn spring-boot:run

# Gradle
./gradlew bootRun
```

打包：
```bash
mvn package -DskipTests        # → target/fast-web.jar
./gradlew bootJar              # → build/libs/fast-web.jar
```

Docker 构建（默认取 Maven 产物，Gradle 用户传 JAR_FILE）：
```bash
docker build -t fast-web:2.0.0 .
docker build -t fast-web:2.0.0 --build-arg JAR_FILE=build/libs/fast-web.jar .
```

### 3. 启动 AI 工作流

在 OMP（或其他支持的 AI 工具）中：
```
用 workflow-start 开始
```
或 `/skill:workflow-start`。AI 会自动检测当前状态并路由到正确的开发阶段。

### 4. 日常开发

- **大型功能**：走 Spec-first 工作流（需求 → 设计 → 契约 → 实现 → Review → 归档）
- **小型变更**（≤4 文件纯配置/文档）：tweak 模式，直接编辑
- **CLI 工具**：`npx spec-superflow@latest <command>`（list / validate / doctor / execution 等）

## AI 协作架构

### 统一配置中心（`.ai/`）

所有 AI 工具共享一套配置，通过软链接分发到各工具的发现路径：

| 工具 | 发现路径 | 指向 |
|------|----------|------|
| OMP | `.omp/skills` | `.ai/skills` |
| OMP | `.omp/rules` | `.ai/rules` |
| OMP | `.omp/hooks` | `.ai/hooks` |
| 通用 | `AGENTS.md` | 项目根（各工具原生读取） |

### 工作流引擎（spec-superflow）

来源：[MageByte-Zero/spec-superflow](https://github.com/MageByte-Zero/spec-superflow)（v0.10.0）

9 个 skill 覆盖完整开发生命周期：

| Skill | 阶段 | 职责 |
|-------|------|------|
| `workflow-start` | 入口 | 状态检测、路由、阻止非法跳转 |
| `need-explorer` | 探索 | 一次一问 + 方案对比 + 推荐 |
| `spec-writer` | 规格 | 产出 proposal/specs/design/tasks |
| `contract-builder` | 桥接 | 压缩为 execution-contract.md |
| `build-executor` | 执行 | TDD + SDD 子代理 + Review Gate |
| `bug-investigator` | 调试 | 4 阶段根因分析 |
| `code-reviewer` | 审查 | 结构化审查，三级问题分级 |
| `release-archivist` | 收尾 | 验证 + 归档 + 风险总结 |
| `spec-merger` | 收尾 | Delta Spec → 主规范合并 |

### 团队协作要点

- **配置入库**：`.ai/`、`.omp/`、`.spec-superflow/` 全部随 git 提交，团队成员 clone 后 AI 工具自动就绪
- **规则统一**：`AGENTS.md` 定义代码约定，所有 AI 工具遵守相同规范
- **流程强制**：phase-guard 规则（TTSR 条件触发）确保大型变更必须走 Spec-first 流程
- **工件可追溯**：每个变更产出 proposal → spec → design → tasks → contract，全程可审计

### 管理命令

```bash
./.ai/ai-manage.sh sync      # 建立/修复 .omp/ 软链接
./.ai/ai-manage.sh status    # 查看状态
./.ai/ai-manage.sh list      # 列出已安装 skill
```

### 升级工作流引擎

```bash
cd .spec-superflow && git pull origin main && cd ..
# .ai/skills/ 内的软链接指向不变，无需重建
```

详细适配说明见 `.spec-superflow/OMP-ADAPTER.md`，配置详情见 `.ai/README.md`。

## 技术栈

- JDK：***21+***
- 构建工具：Maven / Gradle（双构建，共享同一源码）
- 基础框架：SpringBoot 4.1.0
- ORM：Mybatis-Plus
- 日志：Slf4j + Log4j2
- 数据库：Mysql（Hikari 连接池）
- 缓存：Redis（Redisson 客户端）
- 应用监控：Actuator
- 容器：Docker
- 接口管理：Spring doc + knife4j
- 工具包：Guava / Lombok / Hutool
- AI 工作流：spec-superflow + OMP

## Web 基础功能

- 日志：Log4j2 持久化到 `logs/`，配置见 `src/main/resources/log4j2-spring.xml`
- ORM：MyBatis-Plus（Mysql 分页、mapperScan `com.*.*.dao`、主键自增、驼峰映射）
- 接口文档：`http://127.0.0.1:8080/web/doc.html`
- 安全：mica-xss 过滤、cors（`WebMvcConfig#addCorsMappings`）
- 参数校验：jakarta.validation + 自定义注解
- 监控：Actuator health 端点，basePath=info

## 扩展功能

- 参数校验
  - `com.lv.fast.common.valid.EnumCheck` — 枚举限制校验
  - `com.lv.fast.common.valid.Phone` — 手机号格式校验
  - `com.lv.fast.common.valid.group.AddStrategy` / `UpdateStrategy` — 分组策略
- 全局对象
  - `com.lv.fast.common.entity.RestResult` — 统一响应
  - `com.lv.fast.common.entity.Code` / `Describe` — 标识码/描述接口
  - `com.lv.fast.common.constant.RestResultCodeConstant` / `RestResultDescribeConstant`
  - `com.lv.fast.common.enums.RestResultEnum`
- 异常
  - `com.lv.fast.exception.BusinessException` — 自定义业务异常
  - `com.lv.fast.exception.GlobalExceptionHandler` — 全局捕获
- 分页
  - `com.lv.fast.common.entity.PageQuery` — 分页入参
  - `com.lv.fast.common.util.PageUtil` — 分页工具
- 请求日志
  - `com.lv.fast.common.aop.RequestLogAop` — 自动记录 controller 请求日志
  - 输出到 `logs/request*.log`
- 业务日志
  - `com.lv.fast.common.log.LogRecord` — 注解式业务日志（参考[美团方案](https://mp.weixin.qq.com/s/JC51S_bI02npm4CE5NEEow)）
  - 扩展点：`OperatorService`（操作者）、`LogRecordService`（存储）
  - 注意：基于 Spring AOP，嵌套调用需满足 AOP 规则
- Redis 缓存注解（可选）
  - `com.lv.fast.redis.RedisHashCache` — Hash 缓存
  - `com.lv.fast.redis.RedisEvict` — 清除缓存
  - `com.lv.fast.redis.RedisBatchEvict` — 批量清除

## 业务编码参考

`com.lv.fast.module` 按模块划分业务代码，`com.lv.fast.module.test` 为示例模块（可删除）。
MVC 分层：`controller` / `service` / `dao` / `dto` / `enums`。
