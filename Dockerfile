FROM maven:3.9.11-eclipse-temurin-25 AS build
WORKDIR /build
COPY pom.xml .
RUN mvn -B dependency:go-offline
COPY src ./src
RUN mvn -B clean package

FROM eclipse-temurin:25-jre
WORKDIR /app
RUN groupadd --system app && useradd --system --gid app app
COPY --from=build --chown=app:app /build/target/app.jar /app/app.jar
USER app
EXPOSE 8080
ENV TZ=America/Sao_Paulo
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
