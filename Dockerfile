FROM gradle:8.4-jdk17 AS builder
WORKDIR /app
COPY . .
RUN gradle build

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar onlinestore.jar
COPY config/application-docker.yaml ./config/
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "onlinestore.jar", "--spring.config.location=classpath:/,file:./config/application-docker.yaml"]
