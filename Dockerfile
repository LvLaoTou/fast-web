# 这里没有使用alpine镜像是因为open jdk官方没有维护基于alpine的jre镜像 可自行考虑替换为基于alpine的jre镜像
FROM openjdk:8-jre-slim-buster as builder
WORKDIR application
COPY target/*.jar application.jar
RUN java -Djarmode=layertools -jar application.jar extract

FROM openjdk:8-jre-slim-buster
ENV TZ=Asia/Shanghai
EXPOSE 8080
WORKDIR application
COPY --from=builder application/dependencies/ ./
COPY --from=builder application/spring-boot-loader/ ./
COPY --from=builder application/snapshot-dependencies/ ./
COPY --from=builder application/application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]