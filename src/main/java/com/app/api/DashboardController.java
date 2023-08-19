package com.app.api;

import com.app.api.dto.InvestedInfo;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import liquibase.repackaged.com.opencsv.bean.CsvToBean;
import liquibase.repackaged.com.opencsv.bean.CsvToBeanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/dashboard")
public class DashboardController {

  List<InvestedInfo> testdata = new ArrayList<>();

  @GetMapping("hello")
  public ResponseEntity hello() {
    log.info("Hello");
    return ResponseEntity.ok().body("Hello, App is up - " + LocalDateTime.now());
  }

  @GetMapping
  public ResponseEntity<List<InvestedInfo>> getInvestedInfo() {
    return ResponseEntity.ok().body(testdata);
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @PostMapping("/upload")
  public ResponseEntity uploadCSVFile(@RequestParam("file") MultipartFile file) throws IOException {
    try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
      CsvToBean<InvestedInfo> csvToBean =
          new CsvToBeanBuilder(reader)
              .withType(InvestedInfo.class)
              .withIgnoreLeadingWhiteSpace(true)
              .build();

      List<InvestedInfo> uploadedData = csvToBean.parse();
      testdata.addAll(uploadedData);
      log.info("Uploaded data: {}", uploadedData);
    } catch (Exception exception) {
      log.error("Unable to parse {}", exception);
      return ResponseEntity.badRequest().build();
    }
    return ResponseEntity.accepted().build();
  }
}
