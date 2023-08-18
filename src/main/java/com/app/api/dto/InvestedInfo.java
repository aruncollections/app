package com.app.api.dto;

import liquibase.repackaged.com.opencsv.bean.CsvBindByName;
import lombok.Data;

@Data
public class InvestedInfo {

  @CsvBindByName private Long investorId;

  @CsvBindByName private String investorName;

  @CsvBindByName private Double investedAmount;

  @CsvBindByName private Double updatedAmount;

  @CsvBindByName private String uploadedBy;
}
