# Use a base image with Java 17 (recommended for Spring Boot 3+)
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven project files (pom.xml first for caching)
COPY pom.xml .
COPY src ./src

# Install Maven and build the application
# We install Maven first as it's not in the slim image by default
# -DskipTests is used to skip running tests during deployment build
RUN apt-get update && apt-get install -y maven && \
    mvn clean install -DskipTests

# Expose the port your Spring Boot application listens on (default for Spring Boot is 8080)
EXPOSE 8080

# Command to run your Spring Boot application
# This assumes your JAR file is generated in the 'target/' directory
# The '*' will match the actual JAR name (e.g., myproject-0.0.1-SNAPSHOT.jar)
CMD ["java", "-jar", "oauth2-user-sync-home-page-demo-0.0.1-SNAPSHOT"]