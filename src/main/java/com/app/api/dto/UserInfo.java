package com.app.api.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {

  @NonNull
  private String emailId;

  @NonNull
  private String password;

  @NonNull
  private String firstName;

  @NonNull
  private String lastName;

  private boolean isActive;
}
