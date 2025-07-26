# Use a base image with Java 17 for your Spring Boot application
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Install Maven within the Docker image. This is needed to build your project.
RUN apt-get update && apt-get install -y maven

# Copy your project's pom.xml first. This helps Docker cache the dependencies.
COPY pom.xml .

# Copy your source code (all files in the 'src' directory)
COPY src ./src

# Build your Spring Boot application.
# 'mvn clean package' ensures the JAR file is created.
# '-DskipTests' skips running tests, which speeds up the build for deployment.
RUN mvn clean package -DskipTests

# IMPORTANT: This line copies your compiled JAR file to the root of the /app directory inside the container.
# It also renames it to 'app.jar' for easier access.
# YOU MUST ENSURE THE PART AFTER 'target/' IS THE EXACT NAME OF YOUR JAR FILE.
# Based on your previous message, your JAR file name is 'oauth2-user-sync-home-page-demo-0.0.1-SNAPSHOT.jar'
COPY target/oauth2-user-sync-home-page-demo-0.0.1-SNAPSHOT.jar app.jar

# Expose the port that your Spring Boot application listens on (default for Spring Boot is 8080)
EXPOSE 8080

# Define the command to run your application when the Docker container starts.
# It now runs 'app.jar', which is located at /app/app.jar.
CMD ["java", "-jar", "app.jar"]