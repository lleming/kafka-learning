package dev.simar.producer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/kaffka")
public class KaffkaController {

  private final KaffkaService kaffkaService;

  public KaffkaController(KaffkaService kaffkaService) {
    this.kaffkaService = kaffkaService;
  }

  @PostMapping("/publish/{name}")
  public void publishMessage(@PathVariable("name") String name, @RequestBody String message) {
    kaffkaService.publishMessage(name, message);
  }
}
