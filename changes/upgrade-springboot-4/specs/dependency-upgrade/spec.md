# dependency-upgrade Specification

## MODIFIED Requirements

### Requirement: Spring Boot Parent 版本升级

系统 SHALL 将 `spring-boot-starter-parent` 版本从 `3.2.1` 升级到 `4.1.0`，`java.version` 属性 MUST 设置为 `21`。

#### Scenario: Parent 版本正确

- **WHEN** 查看 pom.xml 的 parent 声明
- **THEN** `spring-boot-starter-parent` 版本为 `4.1.0`
- **AND** `java.version` 属性为 `21`

### Requirement: MyBatis-Plus Starter 迁移

系统 SHALL 将 MyBatis-Plus 依赖从 `mybatis-plus-spring-boot3-starter:3.5.5` 替换为 `mybatis-plus-spring-boot4-starter:3.5.16`。

#### Scenario: MyBatis-Plus artifact 正确

- **WHEN** 查看 pom.xml 的 MyBatis-Plus 依赖
- **THEN** artifactId 为 `mybatis-plus-spring-boot4-starter`
- **AND** 版本为 `3.5.16`

### Requirement: Knife4j Starter 迁移

系统 SHALL 将 Knife4j 依赖从 `com.github.xiaoymin:knife4j-openapi3-jakarta-spring-boot-starter:4.5.0` 替换为 `com.baizhukui:knife4j-openapi3-boot4-spring-boot-starter:5.0.6`。

#### Scenario: Knife4j 依赖正确

- **WHEN** 查看 pom.xml 的 Knife4j 依赖
- **THEN** groupId 为 `com.baizhukui`
- **AND** artifactId 为 `knife4j-openapi3-boot4-spring-boot-starter`
- **AND** 版本为 `5.0.6`

### Requirement: 工具库版本升级

系统 SHALL 将工具库依赖升级到指定版本：Hutool `5.8.36`、Guava `33.4.8-jre`、Disruptor `4.0.0`、Mica-XSS `4.0.6`。

#### Scenario: 工具库版本正确

- **WHEN** 查看 pom.xml 的 properties 和依赖声明
- **THEN** `hutool.version` 为 `5.8.36`
- **AND** `guava.version` 为 `33.4.8-jre`
- **AND** `disruptor.version` 为 `4.0.0`
- **AND** `mica-xss.version` 为 `4.0.6`

### Requirement: 构建插件升级

系统 SHALL 将 `docker-maven-plugin` 从 `0.40.0` 升级到 `0.46.0`。

#### Scenario: Docker 插件版本正确

- **WHEN** 查看 pom.xml 的 docker-maven-plugin 版本
- **THEN** 版本为 `0.46.0`

### Requirement: Web Starter 替换

系统 SHALL 将 `spring-boot-starter-web` 替换为 `spring-boot-starter-webmvc`，因为 Spring Boot 4.x 已废弃旧 artifact。

#### Scenario: Web Starter 正确

- **WHEN** 查看 pom.xml 的 web 相关依赖
- **THEN** 存在 `spring-boot-starter-webmvc`
- **AND** 不存在 `spring-boot-starter-web`

### Requirement: 依赖升级后编译通过

升级完成后，`mvn compile -DskipTests` MUST 无编译错误。

#### Scenario: 编译成功

- **WHEN** 执行 `mvn compile -DskipTests`
- **THEN** 构建成功，退出码为 0
