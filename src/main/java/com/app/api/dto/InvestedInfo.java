package com.app.api.dto;

import java.time.LocalDate;
import liquibase.repackaged.com.opencsv.bean.CsvBindByName;
import lombok.Data;

@Data
public class InvestedInfo {

  private String email;

  @CsvBindByName private Long investorId;

  @CsvBindByName private String investorName;

  @CsvBindByName private Double investedAmount;

  @CsvBindByName private String instrument;

  @CsvBindByName private Double updatedAmount;

  @CsvBindByName private String uploadedBy;

  private String updatedBy;

  private LocalDate uploadedDate = LocalDate.now();
}
