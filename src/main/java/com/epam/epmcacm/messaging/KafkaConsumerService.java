package com.epam.epmcacm.messaging;

import com.epam.epmcacm.model.Song;
import com.epam.epmcacm.rest.client.ResourceServiceRestClient;
import com.epam.epmcacm.model.Resource;
import com.epam.epmcacm.rest.client.SongServiceRestClient;
import com.epam.epmcacm.util.FileUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

@Service
public class KafkaConsumerService {
    private final Logger logger = LoggerFactory.getLogger(KafkaConsumerService.class);

    KafkaTemplate kafka;

    ObjectMapper mapper;

    SongServiceRestClient songServiceRestClient;

    ResourceServiceRestClient resourceServiceRestClient;

    private CountDownLatch latch = new CountDownLatch(1);
    private Resource payload;

    @Value("${resource.kafka.topic}")
    private String topic;

    public KafkaConsumerService(KafkaTemplate kafka, ObjectMapper mapper, SongServiceRestClient songServiceRestClient, ResourceServiceRestClient resourceServiceRestClient) {
        this.kafka = kafka;
        this.mapper = mapper;
        this.songServiceRestClient = songServiceRestClient;
        this.resourceServiceRestClient = resourceServiceRestClient;
    }

    @KafkaListener(topics = "${resource.kafka.topic}", containerFactory = "kafkaRetryListenerContainerFactory")
    public void resourceServiceListener(ConsumerRecord<String, String> consumerRecord) throws IOException, InvalidDataException, UnsupportedTagException {
        logger.info("Kafka Topic {} received message with id: {} and resource: {} ",topic, consumerRecord.key(), consumerRecord.value());
        payload = mapper.readValue(consumerRecord.value(), Resource.class);
        latch.countDown();
        saveSongMetadata();
    }

    private Song saveSongMetadata() throws InvalidDataException, UnsupportedTagException, IOException {
        ResponseEntity<ByteArrayResource> result = resourceServiceRestClient.getResourceById(payload.getId());
        if(result.getStatusCode() == HttpStatus.OK) {
            Song songMetadataDto = Song.createSongDTOForClientRequest(payload, result.getBody().getByteArray());
            songServiceRestClient.saveSong(songMetadataDto);
            return songMetadataDto;
        }
        throw new InvalidDataException("Didn't get resource binary to create song metadata!");
    }

    public void resetLatch() {
        latch = new CountDownLatch(1);
    }

    public Resource getPayload() {
        return payload;
    }

    public CountDownLatch getLatch() {
        return latch;
    }
}
