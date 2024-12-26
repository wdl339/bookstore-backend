# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-alpine

# Set the working directory
WORKDIR /app

# Copy the project JAR file into the container at /app
COPY target/bookstore-backend-0.0.1-SNAPSHOT.jar /app/bookstore-backend.jar

# Make port 8080 available to the world outside this container
EXPOSE 8080

# Run the JAR file
ENTRYPOINT ["java", "-jar", "bookstore-backend.jar"]