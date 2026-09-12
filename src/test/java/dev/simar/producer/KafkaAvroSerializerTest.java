package dev.simar.producer;

import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.common.serialization.StringSerializer;

class KafkaAvroSerializerTest {

  Properties kafkaProperties = new Properties();
  KafkaProducer<String, Customer> producer;

  {
    kafkaProperties.put("bootstrap.servers", "localhost:9092");
    kafkaProperties.put("key.serializer",
        "org.apache.kafka.common.serialization.StringSerializer");
    kafkaProperties.put("value.serializer",
        "org.apache.kafka.common.serialization.StringSerializer");
    kafkaProperties.put("acks", "1");
    kafkaProperties.put("buffer.memory", 67108864);
    kafkaProperties.put("batch.size", 32768);
    producer =
        new KafkaProducer<>(kafkaProperties, new StringSerializer(), new CustomerSerializer());
  }
}
