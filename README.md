# resource-processor

## This service used to process MP3 source data. This service does not have a web interface and is used for data processing. Spring boot app, able to extract MP3 metadata for further storing of this data using songs metadata api. Communicates through API Gateway with [resource-service](https://github.com/Branjash/resource-service) and [songs-service](https://github.com/Branjash/songs-service) via exposed REST APIs on both services. Also has async communication on file addition with `resource-service` via `Kafka` messaging platform.

**NOTE!** - *requires that 

### Startup 

**Prerequisits:** 

- Docker engine installed
- `docker-compose` app installed
- `docker-compose` from [resource-service](https://github.com/Branjash/resource-service) be triggered first for additional services required
- OPTIONAL: local `maven` installed, embeded wrapped can be used instead

1. Since it is a maven project, first build it using your local installed maven or using maven wrapper inside the project
   ```bash
    mvn clean package
   ```
    or using maven wrapper
    ```bash
    mvnw clean package
    ```
  
2. Inside project root, build local docker image of the project(project already contains prepared `Dockerfile`
   ```bash
   docker build -t resource-processor-service-local-image .
   ```
     
3. After checking that the image is properly built and ready, start `resource-processor-service` and it's corresponding containers [eureka-server](https://github.com/Branjash/eureka-server) and [eureka-client](https://github.com/Branjash/eureka-client) API gateway.
   ```bash
   docker-compose up -d
   ```
    
5. Finally you can chech if the containers are running and check logs to if they are running properly
   - check are they running
     ```bash
     docker ps
     ```
   - check are they running properly by checking logs (**container_id** - value listed after running previous command)
     ```bash
     docker logs -f *container_id* 
     ```
     
