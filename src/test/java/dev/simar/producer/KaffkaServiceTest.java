package dev.simar.producer;

import java.time.Instant;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = KaffkaService.class)
class KaffkaServiceTest {

  @Autowired
  KaffkaService kaffkaService;

  @Test
  void testSendingMessage() throws InterruptedException {
    java.util.concurrent.CountDownLatch starter = new CountDownLatch(1);
    java.util.concurrent.CountDownLatch finisher = new CountDownLatch(3);
    SendMessage sendMessage0 = new SendMessage(0, kaffkaService, starter, finisher);
    SendMessage sendMessage1 = new SendMessage(1, kaffkaService, starter, finisher);
    SendMessage sendMessage2 = new SendMessage(2, kaffkaService, starter, finisher);
    ExecutorService executor = Executors.newFixedThreadPool(3);
    executor.submit(sendMessage0);
    executor.submit(sendMessage1);
    executor.submit(sendMessage2);
    starter.countDown();
    finisher.await();
  }

  @Test
  void testSendingMessageAsync() throws InterruptedException {
    java.util.concurrent.CountDownLatch starter = new CountDownLatch(1);
    java.util.concurrent.CountDownLatch finisher = new CountDownLatch(3);
    SendMessageAsync sendMessage0 = new SendMessageAsync(0, kaffkaService, starter, finisher);
    SendMessageAsync sendMessage1 = new SendMessageAsync(1, kaffkaService, starter, finisher);
    SendMessageAsync sendMessage2 = new SendMessageAsync(2, kaffkaService, starter, finisher);
    ExecutorService executor = Executors.newFixedThreadPool(3);
    executor.submit(sendMessage0);
    executor.submit(sendMessage1);
    executor.submit(sendMessage2);
    starter.countDown();
    finisher.await();
  }

  static class SendMessage implements Runnable {
    private final int number;
    private final CountDownLatch starter;
    private final CountDownLatch finisher;
    private final KaffkaService kaffkaService;

    SendMessage(int number, KaffkaService kaffkaService, CountDownLatch starter,
                CountDownLatch finisher) {
      this.number = number;
      this.kaffkaService = kaffkaService;
      this.starter = starter;
      this.finisher = finisher;
    }

    @Override
    public void run() {
      try {
        starter.await();
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
      int counter = 0;
      for (int i = 0; i < 100_000; i++) {
        kaffkaService.publishMessage("test",
            number + ":" + counter++ + ": => Hello, Kafka! " + Instant.now());
      }
      finisher.countDown();
    }
  }

  static class SendMessageAsync implements Runnable {
    private final int number;
    private final CountDownLatch starter;
    private final CountDownLatch finisher;
    private final KaffkaService kaffkaService;

    SendMessageAsync(int number, KaffkaService kaffkaService, CountDownLatch starter,
                     CountDownLatch finisher) {
      this.number = number;
      this.kaffkaService = kaffkaService;
      this.starter = starter;
      this.finisher = finisher;
    }

    @Override
    public void run() {
      try {
        starter.await();
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
      int counter = 0;
      for (int i = 0; i < 100_000; i++) {
        kaffkaService.publishMessageAsync("test",
            number + ":" + counter++ + ": => Hello, Kafka! " + Instant.now());
      }
      finisher.countDown();
    }
  }
}
