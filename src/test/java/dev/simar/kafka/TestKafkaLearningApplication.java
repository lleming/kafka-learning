package dev.simar.kafka;

import org.springframework.boot.SpringApplication;

public class TestKafkaLearningApplication {

	public static void main(String[] args) {
		SpringApplication.from(KafkaLearningApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
