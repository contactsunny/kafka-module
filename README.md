# Apache Kafka Module

## Usage

Clone this repo and build locally so that the artifact is installed to your local Maven repository. Use the following command to build and install:

```shell
mvn clean install
```

### Add Maven Dependency

In your project, add the Maven dependency as follows:

```xml
<dependency>
    <groupId>com.contactsunny.poc</groupId>
    <artifactId>kafka-module</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

### Initialization

```java
public class App {
	
	public static void main(String[] args) {

		/*
        Create properties with Kafka servers and group ID information
         */
		Properties properties = new Properties();
		properties.put("kafkaBootstrapServers", "localhost:9092");
		properties.put("groupId", "thetechcheck");
		properties.put("zookeeperHost", "localhost:2181");

		try {
			/*
            Craete a KafkaModule object, another object of a class which implements
            the KafkaConsumerImplementation interface, and then listen to the
            topic using the listenToTopic() method.
             */
		    KafkaModule kafkaModule = new KafkaModule(properties);
		    CustomKafkaConsumer customKafkaConsumer = new CustomKafkaConsumer();
		    kafkaModule.listenToTopic("thetechcheck", customKafkaConsumer);
		} catch (ValidationException e) {
		    e.printStackTrace();
		}

	}
}
```

### Consuming Message

```java
public class CustomKafkaConsumer implements KafkaConsumerImplementation {

    @Override
    public void handleMessage(ConsumerRecord<String, String> consumerRecord, KafkaConsumer<String, String> kafkaConsumer) {

        String message = consumerRecord.value();

        System.out.println("Received message: " + message);

        Map<TopicPartition, OffsetAndMetadata> commitMessage = new HashMap<>();

        commitMessage.put(new TopicPartition(consumerRecord.topic(),consumerRecord.partition()),
                new OffsetAndMetadata(consumerRecord.offset() + 1));

        kafkaConsumer.commitSync(commitMessage);
    }
}
```