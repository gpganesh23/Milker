# --- STAGE 1: Build the Spring Boot application ---
# Use a Maven image to build your application
FROM maven:3.8.5-openjdk-17 AS build

# Set the working directory inside the build stage
WORKDIR /app

# Copy your project files into the build stage
COPY pom.xml .
COPY src ./src

# Build the application
# 'mvn clean package' creates the JAR in target/
# '-DskipTests' skips tests for faster deployment builds
RUN mvn clean package -DskipTests

# --- STAGE 2: Create the final, smaller runtime image ---
# Use a slim OpenJDK image for the final application
FROM openjdk:17-jdk-slim

# Set the working directory inside the final stage
WORKDIR /app

# Copy the JAR file from the 'build' stage into the final image
# We copy 'app.jar' from the build stage, which contains your compiled application
# Replace 'oauth2-user-sync-home-page-demo-0.0.1-SNAPSHOT.jar' with your exact JAR name if different
COPY --from=build /app/target/oauth2-user-sync-home-page-demo-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your Spring Boot application listens on
EXPOSE 8080

# Command to run your Spring Boot application
CMD ["java", "-jar", "app.jar"]