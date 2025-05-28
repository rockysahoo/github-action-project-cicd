FROM gradle:8-jdk21-alpine AS builder
WORKDIR /app

# Copy all necessary files
COPY build.gradle settings.gradle ./
COPY src ./src

# Build with explicit dependency resolution
RUN gradle clean build --no-daemon --stacktrace

FROM openjdk:21-jre-alpine
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]