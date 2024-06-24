FROM gradle:8.8.0-jdk22 as builder

# Set the working directory in the container
WORKDIR /app

# Copy the Gradle wrapper and build files
COPY gradlew /app/gradlew
COPY gradle /app/gradle

# Copy all the project files
COPY . /app

# Make the Gradle wrapper script executable
RUN chmod +x gradlew

# Build the application
RUN ./gradlew build

# Use the official OpenJDK 11 image for running the application
FROM openjdk:22-jre-slim

# Set the working directory in the container
WORKDIR /app

# Copy the built application from the builder stage
COPY --from=builder /app/build/libs/*.jar /app/app.jar

# Expose the port the app runs on (optional, change if necessary)
EXPOSE 8080

# Set environment variables for database configuration
# ENV SPRING_DATASOURCE_URL=${SPRING_DATASOURCE_URL}
# ENV SPRING_DATASOURCE_USERNAME=${SPRING_DATASOURCE_USERNAME}
# ENV SPRING_DATASOURCE_PASSWORD=${SPRING_DATASOURCE_PASSWORD}
# ENV SPRING_JPA_HIBERNATE_DDL_AUTO=${SPRING_JPA_HIBERNATE_DDL_AUTO}

# Run the application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]