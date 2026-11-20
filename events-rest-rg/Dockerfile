# Build the jar first: mvn -B package -DskipTests (from the repository root)
FROM eclipse-temurin:8-jre
WORKDIR /app
COPY target/events-rest-rg-*.jar app.jar
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
