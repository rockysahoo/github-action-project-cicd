FROM gradle:8-jdk21-alpine AS builder
WORKDIR /app

# Copy all project files
COPY . .

# Build the application
RUN gradle build --no-daemon

FROM openjdk:21-ea-1-jdk-oracle
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]