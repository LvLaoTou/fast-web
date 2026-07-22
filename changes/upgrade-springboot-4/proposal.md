# 变更提案

## 背景（Why）

fast-web 脚手架当前基于 Spring Boot 3.2.1，该版本已远超 EOL（3.5.x 于 2026-06-30 终止 OSS 支持）。继续停留在旧版本意味着无法获得安全补丁、性能优化和新特性（模块化、JSpecify null safety、HTTP Service Client 等）。三方依赖（MyBatis-Plus、Knife4j、Hutool、Guava 等）也均有新版本修复已知漏洞。现在升级可确保脚手架在后续开发中保持技术栈竞争力和安全性。

## 变更内容（What Changes）

- Spring Boot Parent 从 3.2.1 升级到 4.1.0，Java 基线从 17 提升到 21
- MyBatis-Plus artifact 从 `mybatis-plus-spring-boot3-starter` 迁移到 `mybatis-plus-spring-boot4-starter`，版本升级到 3.5.16
- Knife4j 从 `com.github.xiaoymin:knife4j-openapi3-jakarta-spring-boot-starter:4.5.0` 迁移到 `com.baizhukui:knife4j-openapi3-boot4-spring-boot-starter:5.0.6`
- `spring-boot-starter-web` 替换为 `spring-boot-starter-webmvc`（4.x 废弃旧 artifact）
- Hutool 5.8.24→5.8.36、Guava 33.0.0→33.4.8、Disruptor 3.4.4→4.0.0、Mica-XSS 3.1.5.1→4.0.6
- Docker Maven Plugin 0.40.0→0.46.0
- 修复因 Spring Framework 7 / Jackson 3 / Tomcat 11 等底层升级导致的代码兼容问题

## 能力（Capabilities）

### 新增能力

- 无

### 修改能力

- `dependency-upgrade`：pom.xml 依赖版本全量升级
- `code-adaptation`：业务代码适配 Spring Boot 4.x 破坏性变更

## 范围（Scope）

### 范围内（In Scope）

- pom.xml 所有依赖版本升级及 artifact 迁移
- 因框架升级导致的编译错误修复
- application.yml 中废弃/重命名配置项适配
- 编译验证（mvn compile）和单元测试验证（mvn test）

### 范围外（Out of Scope）

- 新功能开发（gRPC、OpenTelemetry、API Versioning 等）
- 数据库 schema 变更
- 部署流程 / Docker 镜像构建变更
- JDK 安装（用户自行处理）

## 影响（Impact）

- 影响的代码区域：pom.xml、JsonUtil、WebMvcConfig、RedisConfig、application.yml、log4j2-spring.xml
- 影响的 API 或接口：无外部 API 变更，仅内部框架适配
- 依赖或涉及的外部系统：Maven Central（依赖下载）、MySQL（驱动由 BOM 管理）、Redis（连接池配置）
