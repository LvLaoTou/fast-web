# 构建阶段：解压 Spring Boot 分层 jar（Boot 3.2+ / 4.x 使用 jarmode=tools）
FROM eclipse-temurin:21-jre AS builder
WORKDIR application
# 默认取 Maven 产物；Gradle 用户：docker build --build-arg JAR_FILE=build/libs/fast-web.jar .
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} application.jar
RUN java -Djarmode=tools -jar application.jar extract --layers --destination extracted

FROM eclipse-temurin:21-jre
ENV TZ=Asia/Shanghai
EXPOSE 8080
WORKDIR application
COPY --from=builder application/extracted/dependencies/ ./
COPY --from=builder application/extracted/spring-boot-loader/ ./
COPY --from=builder application/extracted/snapshot-dependencies/ ./
COPY --from=builder application/extracted/application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]
