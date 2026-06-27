FROM maven:3.9.6-eclipse-temurin-21-alpine AS builder
WORKDIR /build

COPY . .

RUN mvn clean package -pl menu-service -am -DskipTests

FROM eclipse-temurin:21-jre-alpine
RUN addgroup -g 1001 -S appuser && adduser -u 1001 -S appuser -G appuser

WORKDIR /menu-service

COPY --from=builder /build/menu-service/target/*.jar menu-service.jar

USER appuser
EXPOSE 8083

ENTRYPOINT ["java", "-jar", "menu-service.jar"]