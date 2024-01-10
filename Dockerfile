FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/BookReader-0.0.1-SNAPSHOT.jar /app/BookReader.jar

EXPOSE 8082

CMD ["java", "-jar", "BookReader.jar"]