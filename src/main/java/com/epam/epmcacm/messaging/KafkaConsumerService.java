package com.epam.epmcacm.messaging;

import com.epam.epmcacm.ResourceProcessorRestClient;
import com.epam.epmcacm.model.Resource;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {
    private final Logger logger = LoggerFactory.getLogger(KafkaConsumerService.class);
    private ResourceProcessorRestClient resourceProcessorRestClient;
    private KafkaTemplate kafkaTemplate;

    @Value("${resource.kafka.topic}")
    private String topic;

    public KafkaConsumerService(ResourceProcessorRestClient resourceProcessorRestClient, KafkaTemplate kafkaTemplate) {
        this.resourceProcessorRestClient = resourceProcessorRestClient;
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "${resource.kafka.topic}", containerFactory = "kafkaRetryListenerContainerFactory")
    public void resourceServiceListener(@Payload Resource resource, ConsumerRecord<String, Resource> cr) {
        logger.info("Topic {} received message with id: {} and resource: {} ", topic, resource.getId(), resource.toString());
        logger.info(cr.toString());
    }
}
