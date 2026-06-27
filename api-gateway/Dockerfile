FROM maven:3.9.6-eclipse-temurin-21-alpine AS builder
WORKDIR /build

COPY . .

RUN mvn clean package -pl api-gateway -am -DskipTests

FROM eclipse-temurin:21-jre-alpine
RUN addgroup -g 1001 -S appuser && adduser -u 1001 -S appuser -G appuser

WORKDIR /api-gateway

COPY --from=builder /build/api-gateway/target/*.jar api-gateway.jar

USER appuser
EXPOSE 8082

ENTRYPOINT ["java", "-jar", "api-gateway.jar"]