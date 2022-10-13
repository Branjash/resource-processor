# resource-processor

## This service used to process MP3 source data. This service does not have a web interface and is used for data processing. Spring boot app, able to extract MP3 metadata for further storing of this data using songs metadata api.

Before starting Spring Boot app, initialize Kafka messaging service using docker-compose in project root directory
```bash
docker-compose up -d
```
