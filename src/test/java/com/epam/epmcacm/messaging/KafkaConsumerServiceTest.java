package com.epam.epmcacm.messaging;

import com.epam.epmcacm.model.Resource;
import com.epam.epmcacm.model.Song;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1)
public class KafkaConsumerServiceTest {

    @Autowired
    private KafkaConsumerService consumer;

    @Autowired
    private KafkaProducer producer;

    @Value("${resource.kafka.topic}")
    private String topic;


    @Test
    public void givenEmbeddedKafkaBroker_whenSendingWithSimpleProducer_thenMessageReceived() throws Exception {
        Resource resource = produceAndGetTestRecordForConsumption();
        boolean messageConsumed = consumer.getLatch().await(10, TimeUnit.SECONDS);
        assertTrue(messageConsumed);
        assertEquals(resource, consumer.getPayload());
    }

    private Resource produceAndGetTestRecordForConsumption() {
        Resource resource = new Resource();
        resource.setId(0l);
        producer.send(topic, resource);
        return resource;
    }
}