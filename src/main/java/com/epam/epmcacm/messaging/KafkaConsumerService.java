package com.epam.epmcacm.messaging;

import com.epam.epmcacm.ResourceProcessorRestClient;
import com.epam.epmcacm.model.Resource;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    public static final String TOPIC_RESOURCES = "resource-topic";
    private final Logger logger = LoggerFactory.getLogger(KafkaConsumerService.class);
    private ResourceProcessorRestClient resourceProcessorRestClient;
    private KafkaTemplate kafkaTemplate;

    public KafkaConsumerService(ResourceProcessorRestClient resourceProcessorRestClient, KafkaTemplate kafkaTemplate) {
        this.resourceProcessorRestClient = resourceProcessorRestClient;
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = KafkaConsumerService.TOPIC_RESOURCES, groupId = KafkaConsumer.GROUP_ID)
    public void resourceServiceListener(@Payload Resource resource, ConsumerRecord<String, Resource> cr) {
        logger.info("Topic [resource-received] received message with id: {} and resource: {} ", resource.getId(), resource.toString());
        logger.info(cr.toString());
    }
}
