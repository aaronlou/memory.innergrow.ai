# 使用本地 Java 运行时镜像
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# 设置时区
RUN apk add --no-cache tzdata
ENV TZ=Asia/Shanghai

# 创建上传和日志目录
RUN mkdir -p /app/uploads /app/logs

# 直接复制已编译好的 JAR 包
COPY memory-0.0.1-SNAPSHOT.jar app.jar

# 暴露端口
EXPOSE 8080

# 启动命令
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar", "--spring.profiles.active=prod"]
