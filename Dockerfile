FROM openjdk:21-jdk
WORKDIR /app
COPY ./target/SpringBootApi-0.0.1-SNAPSHOT.jar /app
EXPOSE 8080
CMD ["java", "-jar", "SpringBootApi-0.0.1-SNAPSHOT.jar", "--host", "0.0.0.0"]
