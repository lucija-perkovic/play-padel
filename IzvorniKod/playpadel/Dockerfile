FROM alpine/java:17-jre
COPY target/*.jar /playpadel.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/playpadel.jar"]