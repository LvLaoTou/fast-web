# Design: upgrade-springboot-4

## 升级策略

采用**一次性全量升级**策略：直接将 Spring Boot parent 从 3.2.1 跳到 4.1.0，同步替换所有不兼容依赖，然后逐一修复编译错误。

理由：
- 项目体量小（约 40 个 Java 文件），逐步升级（3.2→3.5→4.0→4.1）的中间态验证成本高于直接升级后统一修复
- Spring Boot 官方推荐先升到 3.5 再升 4.x，但本项目代码量不足以让中间态验证产生额外收益

## 依赖变更设计

### 1. Spring Boot Parent

```xml
<!-- 3.2.1 → 4.1.0 -->
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>4.1.0</version>
</parent>
```

`java.version` 从 `17` 改为 `21`。

### 2. MyBatis-Plus

Spring Boot 4 需要专用 starter，artifact 名称变更：

```xml
<!-- 旧: mybatis-plus-spring-boot3-starter:3.5.5 -->
<!-- 新: -->
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus-spring-boot4-starter</artifactId>
    <version>3.5.16</version>
</dependency>
```

`MybatisPlusConfig` 中的 `MybatisPlusInterceptor` API 在 3.5.16 中保持兼容，无需代码变更。

### 3. Knife4j

groupId 和 artifact 均变更：

```xml
<!-- 旧: com.github.xiaoymin:knife4j-openapi3-jakarta-spring-boot-starter:4.5.0 -->
<!-- 新: -->
<dependency>
    <groupId>com.baizhukui</groupId>
    <artifactId>knife4j-openapi3-boot4-spring-boot-starter</artifactId>
    <version>5.0.6</version>
</dependency>
```

Knife4j 5.x 基于 springdoc-openapi 3.x，OpenAPI 注解（`@Tag`、`@Operation`）保持兼容。

### 4. Web Starter

Spring Boot 4.x 中 `spring-boot-starter-web` 标记为废弃（deprecated in favor of `spring-boot-starter-webmvc`），但 4.1.0 仍保留兼容。

**决策**：替换为 `spring-boot-starter-webmvc`，避免废弃警告。

### 5. Disruptor 4.0.0

Disruptor 4.x 移除了 `EventHandler` 的 `onEvent(T event, long sequence, boolean endOfBatch)` 中的部分废弃方法，但 Log4j2 内部使用的 `AsyncLoggerConfigDisruptor` 已适配 4.x。项目仅通过 Log4j2 间接使用 Disruptor，无需代码变更。

### 6. Mica-XSS 4.0.6

Mica-XSS 4.x 适配 Spring Boot 4，API 保持兼容（`XssCleaner`、`XssProperties`）。

### 7. 其他工具库

Hutool、Guava 为小版本升级，API 无破坏性变更。

## 代码适配设计

### Jackson 兼容性

Spring Boot 4 默认 Jackson 3，但 Jackson 2 以废弃形式保留。项目 `JsonUtil` 使用 `com.fasterxml.jackson.databind.ObjectMapper`。

**决策**：检查 `JsonUtil` 是否使用 Jackson 2 特有 API。如果仅使用基础序列化/反序列化，Jackson 3 兼容。如果使用了 Jackson 2 模块（如 `JavaTimeModule`），需要评估迁移路径。

### Redis 配置

`RedisConfig` 中的 `RedisTemplate` 序列化器配置在 Spring Boot 4 中保持兼容。`commons-pool2` 连接池配置无变更。

### Log4j2

`log4j2-spring.xml` 配置格式在 Spring Boot 4 中无变更。Disruptor 4.0.0 与 Log4j2 2.24+ 兼容（Spring Boot 4.1.0 管理的 Log4j2 版本已适配）。

### Validation

`jakarta.validation` 在 Spring Boot 4 中无 API 变更。自定义校验器 `EnumCheckValidator`、`Phone` 无需修改。

### AOP

Spring Framework 7 中 `@Aspect` 切面表达式语法无变更。`RequestLogAop`、`LogRecordAop`、`RedisAop` 无需修改。

## 风险评估

| 风险 | 影响 | 缓解措施 |
|------|------|---------|
| MyBatis-Plus boot4-starter 与 Spring Boot 4.1.0 兼容性 | 启动失败 | 编译+启动验证 |
| Knife4j 5.x 配置变更 | API 文档不可用 | 启动后访问 /doc.html 验证 |
| Jackson 3 默认启用导致序列化行为变更 | 接口响应格式变化 | 检查 JsonUtil 和全局序列化配置 |
| Disruptor 4.0.0 与 Log4j2 版本不匹配 | 异步日志失败 | 依赖 Spring Boot BOM 管理的 Log4j2 版本 |
