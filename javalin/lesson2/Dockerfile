FROM eclipse-temurin:25-jdk-alpine
COPY target/lesson2-1.0-SNAPSHOT-jar-with-dependencies.jar /app.jar
# This is the port that your javalin application will listen on
EXPOSE 8877
ENTRYPOINT ["java", "-jar", "/app.jar"]