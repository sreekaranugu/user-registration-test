package com.example.demo.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRegistrationRequestDto {

  @NotBlank
  String username;

  // NOTE: question never asked for a compulsory small letter
  @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[0-9])(?=.*?[_#$%.]).{8,}$")
  @NotBlank
  String password;

  @NotBlank
  String ipAddress;

}
