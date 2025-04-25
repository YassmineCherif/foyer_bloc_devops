# Use an official OpenJDK runtime as a parent imagee
FROM openjdk:17-jdk-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the built jar file into the container
COPY target/Foyer-0.0.1-SNAPSHOT.jar /app/Foyer.jar

# Expose port 8081 to the outside world
EXPOSE 8081

# Run the jar file when the container starts
ENTRYPOINT ["java", "-jar", "Foyer.jar"]
