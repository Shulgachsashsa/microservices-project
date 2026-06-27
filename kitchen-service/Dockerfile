FROM maven:3.9.6-eclipse-temurin-21-alpine AS builder
WORKDIR /build

COPY . .

RUN mvn clean package -pl kitchen-service -am -DskipTests

FROM eclipse-temurin:21-jre-alpine
RUN addgroup -g 1001 -S appuser && adduser -u 1001 -S appuser -G appuser

WORKDIR /kitchen-service

COPY --from=builder /build/kitchen-service/target/*.jar kitchen-service.jar

USER appuser
EXPOSE 8085

ENTRYPOINT ["java", "-jar", "kitchen-service.jar"]