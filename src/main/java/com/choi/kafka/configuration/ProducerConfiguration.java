package com.choi.kafka.configuration;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

// 1. create a ProducerFactory bean which knows how to create producers based on the configs you provided
// 2. create a KafkaTemplate bean to perform high level operations on your producer:
//    the template is able to do operations such as sending a message to a topic and efficiently hides under-the-hood details from you
@Configuration // when registering beans more than or equal to one
public class ProducerConfiguration {
    private static final String KAFKA_BROKER = "localhost:9092";
    // 1. set up config
    @Bean // register it to Java Bean
    public Map<String, Object> producerConfigurations() {
        Map<String, Object> configurations = new HashMap<>();

        configurations.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_BROKER); // connect to local kafka broker
        configurations.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class); // serialize key with string
        configurations.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class); // serialize value with string

        return configurations;
    }
    // 1. create ProducerFactory bean
    @Bean
    public ProducerFactory<String, String> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigurations());
    }
    // 2. create a producer and return a handle of the producer
    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
