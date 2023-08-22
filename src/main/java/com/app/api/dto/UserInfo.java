package com.app.api.dto;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import lombok.*;

@Data
@Valid
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {

  private Long id;

  @Email(message = "Invalid email format")
  @NonNull private String emailId;

  @NonNull private String password;

  @NonNull private String firstName;

  @NonNull private String lastName;

  private boolean active;
}
