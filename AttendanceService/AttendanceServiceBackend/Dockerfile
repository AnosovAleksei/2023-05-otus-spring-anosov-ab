FROM maven:3.8.3-openjdk-17 as build
COPY pom.xml .
COPY src src
RUN mvn package

FROM openjdk:17-alpine3.14
COPY --from=build /target/attendanceServiceBackend.jar /app/app.jar
EXPOSE 8000
ENTRYPOINT ["java", "-jar", "/app/app.jar"]

