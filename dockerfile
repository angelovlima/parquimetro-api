FROM openjdk:17-jdk-slim
WORKDIR /app
ADD build/libs/parquimetro-api-1.0.0-SNAPSHOT.jar /app/parquimetro-api.jar
ENTRYPOINT ["java", "-jar", "parquimetro-api.jar"]