package com.contactsunny.poc.kafkaModule.interfaces;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public interface KafkaConsumerImplementation {

    void handleMessage(ConsumerRecord<String, String> record, KafkaConsumer<String, String> kafkaConsumer);
}
