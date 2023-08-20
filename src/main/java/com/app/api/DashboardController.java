package com.app.api;

import com.app.api.dto.InvestedInfo;
import com.app.service.investment.InvestmentService;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import liquibase.repackaged.com.opencsv.bean.CsvToBean;
import liquibase.repackaged.com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

  List<InvestedInfo> testdata = new ArrayList<>();

  private final InvestmentService investmentService;

  @GetMapping("hello")
  public ResponseEntity hello() {
    log.info("Hello");
    return ResponseEntity.ok().body("Hello, App is up - " + LocalDateTime.now());
  }

  @GetMapping
  public ResponseEntity<List<InvestedInfo>> getInvestedInfo() {
    val investmentData = investmentService.getInvestmentData();
    return ResponseEntity.ok()
        .body(
            investmentData.stream()
                .map(
                    d -> {
                      val investedInfo = new InvestedInfo();
                      investedInfo.setEmailId(d.getUser().getEmailId());
                      investedInfo.setInstrument(d.getInstrument());
                      investedInfo.setInvestedAmount(d.getInvestedAmount());
                      investedInfo.setUpdatedBy(d.getLastEditedBy());
                      investedInfo.setUploadedDate(d.getUpdatedTime().toLocalDate());
                      investedInfo.setUpdatedAmount(d.getUpdatedAmount());
                      return investedInfo;
                    })
                .collect(Collectors.toList()));
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @PostMapping("/upload")
  public ResponseEntity uploadCSVFile(@RequestParam("file") MultipartFile file) throws IOException {
    try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
      CsvToBean<InvestedInfo> csvToBean =
          new CsvToBeanBuilder(reader).withType(InvestedInfo.class).build();
      List<InvestedInfo> uploadedData = csvToBean.parse();
      investmentService.updateInvestmentData(uploadedData);
      log.info("Uploaded data: {}", uploadedData);
    } catch (Exception exception) {
      log.error("Unable to parse {}", exception);
      return ResponseEntity.badRequest().build();
    }
    return ResponseEntity.accepted().build();
  }
}
