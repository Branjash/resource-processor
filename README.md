# resource-processor

## This service used to process MP3 source data. This service does not have a web interface and is used for data processing. Spring boot app, able to extract MP3 metadata for further storing of this data using songs metadata api.

Before starting Spring Boot app, initialize Kafka messaging service using docker-compose in project root directory
```bash
docker-compose up -d
```
- Added `KafkaConsumerService` and it's configurational class `KafkaConsumerConfiguration` to enable kafka template factory and other settings
- Added REST Client implementation for communicating synchronously with `resource-service`(https://github.com/Branjash/resource-service) application and `songs-service`(https://github.com/Branjash/song-service) application
- Added test coverage, but will be completed for next modules.
