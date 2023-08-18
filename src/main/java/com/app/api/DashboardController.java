package com.app.api;

import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/dashboard")
public class DashboardController {

  @GetMapping("hello")
  public ResponseEntity hello() {
    log.info("xxxx hello");
    return ResponseEntity.ok().body("App running - " + LocalDateTime.now());
  }
}
