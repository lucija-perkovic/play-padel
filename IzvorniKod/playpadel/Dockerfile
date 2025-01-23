# Step 1: Use the official Gradle image with JDK 17 to build the app
FROM gradle:7.5-jdk17 AS build

# Step 2: Set the working directory inside the container
WORKDIR /app

# Step 3: Copy the Gradle wrapper files
COPY gradlew .
COPY gradle gradle/
COPY settings.gradle .
COPY build.gradle .

# Step 4: Copy the source code
COPY src src/

# Step 5: Set executable permission for gradlew
RUN chmod +x gradlew
# Step 6: Run the Gradle build using the wrapper
RUN ./gradlew clean build -x test --no-daemon

# Step 7: Use OpenJDK 17 for the runtime environment (final image)
FROM openjdk:17-jdk

# Step 8: Set the working directory for the runtime
WORKDIR /app

# Step 9: Copy the JAR file from the build stage
COPY --from=build /app/build/libs/*.jar app.jar

ENV PORT=8080
# Step 10: Expose port 8080
EXPOSE $PORT

# Step 11: Define the entrypoint to run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
