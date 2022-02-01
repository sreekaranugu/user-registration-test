package com.example.demo.controller;

import com.example.demo.dto.IpAddressDetails;
import com.example.demo.dto.UserRegistrationRequestDto;
import com.example.demo.service.IpVerificationService;
import java.util.UUID;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRegistrationController {

  private static final String CANADA = "Canada";
  @Autowired
  IpVerificationService ipVerificationService;

  @PostMapping("/register")
  public ResponseEntity<String> registerUser(
      @Valid @RequestBody UserRegistrationRequestDto requestDto) {
    final IpAddressDetails addressDetails =
        ipVerificationService.getIpAddressDetails(requestDto.getIpAddress());
    if (addressDetails.getCountry() != null &&
        addressDetails.getCountry().equalsIgnoreCase(CANADA)) {
      return new ResponseEntity("Welcome " +
          requestDto.getUsername() +
          " from " +
          addressDetails.getCity() +
          ", Your user id is : " +
          UUID.randomUUID(), HttpStatus.CREATED);
    } else {
      return new ResponseEntity("IP Address is not in " + CANADA, HttpStatus.BAD_REQUEST);
    }
  }

}
