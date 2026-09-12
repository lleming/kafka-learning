package dev.simar.producer;

import java.util.Properties;
import java.util.concurrent.Future;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

@Service
public class KaffkaService implements InitializingBean, DisposableBean {

  private KafkaProducer<String, String> producer;

  public void publishMessage(String name, String message) {
    ProducerRecord<String, String> record = new ProducerRecord<>("test", name, message);
    try {
      Future<RecordMetadata> result = producer.send(record);
      result.get(); // Wait for the send to complete
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void publishMessageAsync(String name, String message) {
    ProducerRecord<String, String> record = new ProducerRecord<>("test", name, message);
    producer.send(record, new DemoProducerCallback());
  }

  @Override
  public void destroy() throws Exception {
    producer.close();
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    Properties kafkaProperties = new Properties();

    {
      kafkaProperties.put("bootstrap.servers", "localhost:9092");
      kafkaProperties.put("key.serializer",
          "org.apache.kafka.common.serialization.StringSerializer");
      kafkaProperties.put("value.serializer",
          "org.apache.kafka.common.serialization.StringSerializer");
      kafkaProperties.put("acks", "1");
      kafkaProperties.put("buffer.memory", 67108864);
      kafkaProperties.put("batch.size", 32768);
    }

    this.producer = new KafkaProducer<>(kafkaProperties);
  }

  public void publishMessage(String departmentHR, Customer mary) {
  }

  static class DemoProducerCallback implements Callback {

    @Override
    public void onCompletion(RecordMetadata metadata, Exception e) {
      if (e != null) {
        e.printStackTrace();
      }
    }
  }
}
