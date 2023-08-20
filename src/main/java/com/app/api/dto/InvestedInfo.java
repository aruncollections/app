package com.app.api.dto;

import java.time.LocalDate;
import liquibase.repackaged.com.opencsv.bean.CsvBindByName;
import lombok.Data;

@Data
public class InvestedInfo {

  @CsvBindByName private Long investorId;

  @CsvBindByName(required = true)
  private String emailId;

  private String firstName;

  private String lastName;

  @CsvBindByName private Double investedAmount;

  private String instrument;

  @CsvBindByName private Double updatedAmount;

  private String uploadedBy;

  private String updatedBy;

  private LocalDate uploadedDate;
}
