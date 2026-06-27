FROM maven:3.9.6-eclipse-temurin-21-alpine AS builder
WORKDIR /build

COPY . .

RUN mvn clean package -pl discovery-service -am -DskipTests

FROM eclipse-temurin:21-jre-alpine
RUN addgroup -g 1001 -S appuser && adduser -u 1001 -S appuser -G appuser

WORKDIR /discovery-service

COPY --from=builder /build/discovery-service/target/*.jar discovery-service.jar

USER appuser
EXPOSE 8084

ENTRYPOINT ["java", "-jar", "discovery-service.jar"]