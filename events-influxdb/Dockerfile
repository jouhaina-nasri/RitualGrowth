# Build the jar first: mvn -B package -DskipTests (from the repository root)
FROM eclipse-temurin:8-jre
WORKDIR /app
COPY target/events-influxdb-*.jar app.jar
EXPOSE 8084
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
