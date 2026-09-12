package dev.simar.producer;

import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Test;

class KafkaCustomerSerializerTest {

  Properties kafkaProperties = new Properties();
  KafkaProducer<String, Customer> producer;

  {
    kafkaProperties.put("bootstrap.servers", "localhost:9092");
    kafkaProperties.put("key.serializer",
        "org.apache.kafka.common.serialization.StringSerializer");
    kafkaProperties.put("value.serializer", "dev.simar.producer.CustomerSerializer");
    kafkaProperties.put("acks", "1");
    kafkaProperties.put("buffer.memory", 67108864);
    kafkaProperties.put("batch.size", 32768);
    producer =
        new KafkaProducer<>(kafkaProperties, new StringSerializer(), new CustomerSerializer());
  }

  @Test
  void testSendCustomerToKafka() {
    String departmentIT = "IT";
    String departmentHR = "HR";

    Customer john = new Customer(1, "John Doe");
    Customer mary = new Customer(2, "Mary Johnson");
    ProducerRecord<String, Customer> johnData = new ProducerRecord<>("test", departmentHR, john);
    ProducerRecord<String, Customer> maryData = new ProducerRecord<>("test", departmentIT, mary);
    producer.send(johnData);
    producer.send(maryData);
  }
}
