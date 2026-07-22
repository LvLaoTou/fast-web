# code-adaptation Specification

## MODIFIED Requirements

### Requirement: Jackson API 兼容

`JsonUtil` 中的 Jackson API 调用 MUST 在 Spring Boot 4（Jackson 3 默认）下正常编译和运行。如果使用了 Jackson 2 特有 API，系统 SHALL 迁移到 Jackson 3 等价 API 或显式保留 Jackson 2 兼容层。

#### Scenario: JsonUtil 编译通过

- **WHEN** 执行 `mvn compile`
- **THEN** `JsonUtil.java` 无编译错误

#### Scenario: JSON 序列化行为不变

- **WHEN** 调用 `JsonUtil` 的序列化与反序列化方法
- **THEN** 输出格式与升级前保持一致

### Requirement: WebMvcConfig 兼容

`WebMvcConfig` 中的 `WebMvcConfigurer` 实现 MUST 在 Spring Framework 7 下正常编译。

#### Scenario: WebMvcConfig 编译通过

- **WHEN** 执行 `mvn compile`
- **THEN** `WebMvcConfig.java` 无编译错误

### Requirement: Redis 配置兼容

`RedisConfig` 中的 `RedisTemplate` 序列化器配置 MUST 在 Spring Boot 4 下正常工作。

#### Scenario: RedisConfig 编译通过

- **WHEN** 执行 `mvn compile`
- **THEN** `RedisConfig.java` 无编译错误

### Requirement: 配置文件适配

`application.yml` 和 `application-dev.yml` 中已重命名或废弃的配置属性 MUST 更新为新名称或移除。

#### Scenario: 无废弃配置警告

- **WHEN** 应用启动
- **THEN** 不出现配置属性废弃警告

### Requirement: Log4j2 配置兼容

`log4j2-spring.xml` MUST 在 Spring Boot 4 与 Disruptor 4.0.0 组合下正常工作。

#### Scenario: 异步日志正常

- **WHEN** 应用启动并产生日志
- **THEN** 日志正常输出
- **AND** 不出现 Disruptor 相关异常

### Requirement: 单元测试通过

所有现有单元测试 MUST 在升级后通过。

#### Scenario: 测试通过

- **WHEN** 执行 `mvn test`
- **THEN** 所有测试通过，退出码为 0
