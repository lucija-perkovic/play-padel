FROM alpine/java:17-jre
COPY target/*.jar /playpadel-0.0.1-SNAPSHOT.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/playpadel-0.0.1-SNAPSHOT.jar"]