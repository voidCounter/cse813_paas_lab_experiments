# Build stage
FROM eclipse-temurin:17-jdk-jammy AS builder
WORKDIR /app
COPY . .
RUN ./mvnw clean package -DskipTests

# Run stage
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=builder /app/target/user_validation-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
