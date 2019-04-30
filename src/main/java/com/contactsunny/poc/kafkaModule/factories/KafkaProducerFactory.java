package com.contactsunny.poc.kafkaModule.factories;

import org.apache.kafka.clients.producer.KafkaProducer;

import java.util.Properties;

public class KafkaProducerFactory {

    private static KafkaProducer<String, String> producer;

    private KafkaProducerFactory() {}

    public static KafkaProducer<String, String> getProducer(Properties producerProperties) {

        if (producer == null) {
            producer = new KafkaProducer<>(producerProperties);
        }

        return producer;
    }
}
