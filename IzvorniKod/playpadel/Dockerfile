FROM alpine/java:17-jre
COPY target/*.jar /app-0.0.1-SNAPSHOT.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app-0.0.1-SNAPSHOT.jar"]