package com.epam.epmcacm.messaging;

import com.epam.epmcacm.model.Resource;
import com.epam.epmcacm.model.Song;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducer.class);

    private KafkaTemplate kafkaTemplate;

    public KafkaProducer(KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Value("${resource.kafka.topic}")
    private String topic;

    public void send(String topic, Resource payload) {
        logger.info("sending payload= '{}' to topic='{}'", payload, topic);
        kafkaTemplate.send(topic,String.valueOf(payload.getId()), payload);
    }


}
