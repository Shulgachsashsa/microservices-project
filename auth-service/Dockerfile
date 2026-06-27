FROM maven:3.9.6-eclipse-temurin-21-alpine AS builder
WORKDIR /build

COPY . .

RUN mvn clean package -pl auth-service -am -DskipTests

FROM eclipse-temurin:21-jre-alpine
RUN addgroup -g 1001 -S appuser && adduser -u 1001 -S appuser -G appuser

WORKDIR /auth-service

COPY --from=builder /build/auth-service/target/*.jar auth-service.jar

USER appuser
EXPOSE 8081

ENTRYPOINT ["java", "-jar", "auth-service.jar"]