FROM openjdk:17
COPY build/libs/transactions-0.0.1-SNAPSHOT.jar application.jar
ENTRYPOINT ["java","-jar","/application.jar"]
