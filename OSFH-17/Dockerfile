FROM openjdk:11-jre-slim
RUN mkdir -p /app
COPY /build/libs/Kilyakov-homework-0.0.1-SNAPSHOT.jar /app/app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
