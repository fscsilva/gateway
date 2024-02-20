FROM eclipse-temurin:17

LABEL mentainer="fscsilva0@gmail.com"

WORKDIR /app

COPY target/te-gateway-0.8.0.jar /app/te-gateway.jar

ENTRYPOINT ["java", "-jar", "te-gateway.jar"]