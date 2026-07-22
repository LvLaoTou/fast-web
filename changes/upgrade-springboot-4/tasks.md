# Tasks: upgrade-springboot-4

## 任务列表

### T1: 升级 pom.xml 依赖版本

- 修改 `spring-boot-starter-parent` 版本为 `4.1.0`
- 修改 `java.version` 为 `21`
- 替换 `mybatis-plus-spring-boot3-starter` 为 `mybatis-plus-spring-boot4-starter:3.5.16`
- 替换 Knife4j 依赖为 `com.baizhukui:knife4j-openapi3-boot4-spring-boot-starter:5.0.6`
- 升级 Hutool `5.8.36`、Guava `33.4.8-jre`、Disruptor `4.0.0`、Mica-XSS `4.0.6`
- 升级 `docker-maven-plugin` 为 `0.46.0`
- 替换 `spring-boot-starter-web` 为 `spring-boot-starter-webmvc`
- 更新 properties 中的版本号变量

**涉及文件**: `pom.xml`
**对应需求**: REQ-DEP-01 ~ REQ-DEP-06

### T2: 代码适配与编译修复

- 检查并修复 `JsonUtil` 中的 Jackson API 兼容性
- 检查 `WebMvcConfig` 接口兼容性
- 检查 `RedisConfig` 序列化器配置
- 检查 `application.yml` / `application-dev.yml` 中的废弃/重命名属性
- 修复所有编译错误

**涉及文件**: `src/main/java/com/lv/fast/common/util/JsonUtil.java`, `src/main/java/com/lv/fast/config/WebMvcConfig.java`, `src/main/java/com/lv/fast/redis/RedisConfig.java`, `src/main/resources/application.yml`, `src/main/resources/application-dev.yml`
**对应需求**: REQ-CODE-01 ~ REQ-CODE-07

### T3: 编译验证

- 运行 `mvn compile -DskipTests` 确认无编译错误
- 运行 `mvn test` 确认单元测试通过

**涉及文件**: 无新增文件
**对应需求**: 验收标准
