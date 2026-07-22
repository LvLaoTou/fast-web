# 执行合同

## Intent Lock

- **变更名称**：`upgrade-springboot-4`
- **要解决的问题**：Spring Boot 3.2.1 已远超 EOL，存在安全漏洞风险；三方依赖版本过旧，无法获得最新修复和性能优化
- **范围内**：pom.xml 全量依赖升级（Spring Boot 4.1.0 + 所有三方库）、代码适配（Jackson/WebMvc/Redis/配置文件）、编译和测试验证
- **范围外**：新功能开发（gRPC/OpenTelemetry/API Versioning）、数据库 schema 变更、部署流程变更、JDK 安装

## Approved Behavior

- **已批准需求摘要**：Spring Boot parent 升级到 4.1.0（Java 21）；MyBatis-Plus 迁移到 boot4-starter:3.5.16；Knife4j 迁移到 com.baizhukui:5.0.6；Web Starter 替换为 webmvc；工具库（Hutool/Guava/Disruptor/Mica-XSS）升级；代码兼容适配；编译和测试通过
- **关键场景**：pom.xml 依赖版本正确、MyBatis-Plus/Knife4j artifact 迁移正确、JsonUtil 编译通过、WebMvcConfig/RedisConfig 兼容、配置文件无废弃警告、Log4j2 异步日志正常、单元测试全部通过
- **验收检查**：`mvn compile -DskipTests` 成功、`mvn test` 全部通过

## Design Constraints

- **架构约束**：保持现有模块结构（`com.lv.fast.module.<模块名>`）不变，仅做框架适配
- **接口约束**：不改变任何外部 API 接口签名和响应格式
- **依赖约束**：所有依赖版本必须与 Spring Boot 4.1.0 BOM 兼容；MyBatis-Plus 必须使用 boot4-starter
- **数据约束**：Redis 序列化配置不变，数据库连接配置不变

## Execution Plan

full/hotfix 先运行 `ssf execution recommend`，按任务量和 wave 策略列出可用方式并
推荐一种，同时保存匹配当前 wave 的 recommendation receipt。Agent 展示候选项和理由，
`plan` 和 `revise` 均只接受仍匹配 artifact、contract 和 wave 的凭据；用户通过 `--confirm` 明确确认；选择非推荐方式时
还必须记录 `--acknowledge-recommendation`。Batch Inline 是串行模式，不得描述为并行。批准后，
`ssf execution plan` 会把当前执行计划保存到
`<change>/.superpowers/sdd/execution-plan.json`；该 JSON 是计划的持久化控制面，
不是本 execution contract 的一部分。

## Execution Waves

### Wave 1

- **Wave ID**：`w1-deps`
- **任务**：T1（升级 pom.xml 依赖版本）
- **依赖 wave**：无
- **策略**：`serial`
- **目标**：完成 pom.xml 所有依赖版本升级和 artifact 迁移
- **输入**：当前 pom.xml、版本升级矩阵
- **输出**：更新后的 pom.xml
- **完成标准**：pom.xml 中所有依赖版本与目标矩阵一致
- **Review gate**：review report 路径、base/head SHA、review receipt（`pass` | `fail`）

### Wave 2

- **Wave ID**：`w2-adapt`
- **任务**：T2（代码适配与编译修复）
- **依赖 wave**：`w1-deps`
- **策略**：`serial`
- **目标**：修复所有因 Spring Boot 4.x 升级导致的编译错误和配置兼容问题
- **输入**：Wave 1 更新后的 pom.xml、现有源代码
- **输出**：适配后的 Java 源代码和配置文件
- **完成标准**：`mvn compile -DskipTests` 无编译错误
- **Review gate**：review report 路径、base/head SHA、review receipt（`pass` | `fail`）

### Wave 3

- **Wave ID**：`w3-verify`
- **任务**：T3（编译验证）
- **依赖 wave**：`w2-adapt`
- **策略**：`serial`
- **目标**：验证编译和单元测试全部通过
- **输入**：Wave 2 适配后的完整项目
- **输出**：验证报告
- **完成标准**：`mvn compile -DskipTests` 成功且 `mvn test` 全部通过
- **Review gate**：review report 路径、base/head SHA、review receipt（`pass` | `fail`）

## Test Obligations

- **必须先从失败测试开始的行为**：无（本次为框架升级，不引入新行为；验证依赖现有测试）
- **必需的边界情况**：Jackson 3 序列化/反序列化边界（null 值、日期格式、嵌套对象）
- **回归敏感区域**：JsonUtil 序列化行为、Redis 序列化配置、Log4j2 异步日志、自定义校验器

## Execution Mode

- **可用方式与推荐**：`ssf execution recommend <change-dir> [--wave <id>:<parallel|serial>:<task,...>[:<depends-on,...>]]`
- **用户确认的模式**：待 DP-4 确认
- **推荐理由 / 项目事实**：待 recommend 命令输出
- **非推荐选择的风险确认**：`--acknowledge-recommendation`（若适用）
- **执行计划命令**：`ssf execution plan <change-dir> --mode <mode> --confirm --reason <text> --wave <id>:<parallel|serial>:<task,...>[:<depends-on,...>] [--acknowledge-recommendation]`
- **允许的修订**：将已有计划保留/升级为 `sdd`；先重新 recommend，并以 `--confirm` 生成新 revision 和清除旧 receipt；不允许降级
- **计划 revision / artifact hash**：待 execution plan 确认后填写

## Verification Dimensions

| 维度 | 状态 | 发现 |
|------|------|------|
| Completeness | Pending | — |
| Correctness | Pending | — |
| Coherence | Pending | — |

**总体结论**：Pending

## Review Gates

- **强制审查点**：每个 Execution Wave 完成后记录 `ssf execution review` 的 review receipt
- **阻塞类别**：依赖未通过、review receipt 为 `fail`、缺失或过期
- **收口条件**：所有当前 wave 都有 `pass` review receipt

## Escalation Rules

- **何时回退到 `specifying`**：发现 Spring Boot 4.x 存在 proposal 未覆盖的重大破坏性变更，需要扩展范围
- **何时回退到 `bridging`**：某个三方库无 Spring Boot 4 兼容版本，需要调整依赖策略或替换方案
- **何时不得继续实现**：编译错误无法通过代码适配解决（如三方库根本不兼容 Spring Boot 4）
