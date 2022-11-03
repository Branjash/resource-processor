FROM openjdk:11
LABEL maintainer="branko_rovcanin@epam.com"
VOLUME /resource-processor-service
COPY ./target/resource-processor-0.0.1-SNAPSHOT.jar /usr/app/
EXPOSE 8085
WORKDIR /usr/app
ENTRYPOINT ["java","-jar", "resource-processor-0.0.1-SNAPSHOT.jar"]