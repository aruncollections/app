package com.app.api.dto;

import java.time.LocalDate;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import liquibase.repackaged.com.opencsv.bean.CsvBindByName;
import lombok.Data;

@Data
@Valid
public class InvestedInfo {

  @CsvBindByName private Long investorId;

  @Email(message = "Invalid email format")
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
