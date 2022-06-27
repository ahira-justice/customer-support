FROM adoptopenjdk/openjdk11:latest as build

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN chmod +x /mvnw
RUN ./mvnw install -DskipTests

FROM adoptopenjdk/openjdk11:latest

VOLUME /tmp

COPY --from=build target/*.jar app.jar

EXPOSE 80
EXPOSE 443

USER root
ENTRYPOINT ["java", "-jar", "/app.jar"]