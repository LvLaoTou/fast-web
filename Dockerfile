FROM openjdk:8-jre-slim-buster as builder
ENV TZ=Asia/Shanghai
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
RUN true
COPY --from=builder application/application/ ./
RUN true
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]