package com.epam.epmcacm.messaging;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.FixedBackOff;

import java.util.HashMap;
import java.util.Map;

@TestConfiguration
public class KafkaTestConfig {

    final KafkaProperties kafkaProperties;

    public KafkaTestConfig(KafkaProperties kafkaProperties){
        this.kafkaProperties = kafkaProperties;
    }


    @Bean
    public KafkaAdmin adminTest() {
        return new KafkaAdmin(Map.of(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092"));
    }

    @Bean
    public Map<String, Object> consumerConfigurationTest() {
        Map<String, Object> properties = new HashMap<>(kafkaProperties.buildConsumerProperties());
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return properties;
    }

    @Bean
    ConsumerFactory<String, Object> consumerFactoryTest() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigurationTest());
    }

    @Bean
    ConcurrentKafkaListenerContainerFactory<String, Object> kafkaRetryListenerContainerFactoryTest() {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory();
        factory.setConsumerFactory(consumerFactoryTest());
        factory.setCommonErrorHandler(new DefaultErrorHandler(new FixedBackOff(3000L, 5L)));
        return factory;
    }

}
