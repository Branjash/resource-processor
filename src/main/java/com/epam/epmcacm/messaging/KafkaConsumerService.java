package com.epam.epmcacm.messaging;

import com.epam.epmcacm.rest.client.ResourceServiceRestClient;
import com.epam.epmcacm.model.Resource;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;

@Service
public class KafkaConsumerService {
    private final Logger logger = LoggerFactory.getLogger(KafkaConsumerService.class);
    private ResourceServiceRestClient resourceProcessorRestClient;
    private KafkaTemplate kafkaTemplate;

    private CountDownLatch latch = new CountDownLatch(1);
    private Resource payload;
    @Autowired
    ObjectMapper mapper;

    @Value("${resource.kafka.topic}")
    private String topic;

    public KafkaConsumerService(ResourceServiceRestClient resourceProcessorRestClient, KafkaTemplate kafkaTemplate) {
        this.resourceProcessorRestClient = resourceProcessorRestClient;
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "${resource.kafka.topic}", containerFactory = "kafkaRetryListenerContainerFactory")
    public void resourceServiceListener(ConsumerRecord<String, String> consumerRecord) throws JsonProcessingException {
        logger.info("Topic {} received message with id: {} and resource: {} ",topic, consumerRecord.key(), consumerRecord.value());
        this.payload = mapper.readValue(consumerRecord.value(), Resource.class);
        this.latch.countDown();
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
