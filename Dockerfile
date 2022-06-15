FROM openjdk:8-jdk-alpine as build

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN ./mvnw install -DskipTests

FROM openjdk:8-jdk-alpine

VOLUME /tmp

COPY --from=build target/*.jar app.jar

EXPOSE 80
EXPOSE 443

ENTRYPOINT ["java", "-jar", "/app.jar"]