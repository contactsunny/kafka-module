package com.contactsunny.poc.kafkaModule;

import com.contactsunny.poc.kafkaModule.core.KafkaProperties;
import com.contactsunny.poc.kafkaModule.exceptions.ValidationException;
import com.contactsunny.poc.kafkaModule.factories.KafkaProducerFactory;
import com.contactsunny.poc.kafkaModule.interfaces.KafkaConsumerImplementation;
import com.contactsunny.poc.kafkaModule.utils.ValidationUtil;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;

import java.util.*;

import static com.contactsunny.poc.kafkaModule.core.CustomConstants.*;

public class KafkaModule {

    private KafkaProperties kafkaProperties;

    public KafkaModule(Properties properties) throws ValidationException {

//        validateProperties(properties);

        kafkaProperties = new KafkaProperties();
        kafkaProperties.setKafkaBootstrapServers(properties.getProperty(KAFKA_BOOTSTRAP_SERVERS));
        kafkaProperties.setGroupId(properties.getProperty(GROUP_ID));
        kafkaProperties.setZookeeperHost(properties.getProperty(ZOOKEEPER_HOST));
        kafkaProperties.setConsumerProperties();
        kafkaProperties.setProducerProperties();
    }

    private void validateProperties(Properties properties) throws ValidationException {

        List<String> requiredProperties = Arrays.asList(KAFKA_BOOTSTRAP_SERVERS, GROUP_ID, ZOOKEEPER_HOST);

        List<String> propertiesArray = Arrays.asList((String[]) properties.stringPropertyNames().toArray());

        ValidationUtil.runValidation(propertiesArray, requiredProperties);
    }

    public KafkaProducer<String, String> getProducer() {
        return KafkaProducerFactory.getProducer(kafkaProperties.getProducerProperties());
    }

    public void listenToTopic(String topic, KafkaConsumerImplementation kafkaConsumerImplementation) {

        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(kafkaProperties.getConsumerProperties());
        kafkaConsumer.subscribe(Arrays.asList(topic));

        while(true) {

            ConsumerRecords<String, String> records = kafkaConsumer.poll(100);

            for (ConsumerRecord<String, String> record : records) {

                /*
                 * When a message is received, sending it to the
                 * handleMessage() function to continue processing
                 * the message.
                 */
                kafkaConsumerImplementation.handleMessage(record, kafkaConsumer);

                break;
            }
        }
    }
}
