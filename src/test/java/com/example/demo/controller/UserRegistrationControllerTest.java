package com.example.demo.controller;

import com.example.demo.dto.UserRegistrationRequestDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserRegistrationControllerTest {

  @LocalServerPort
  int port;
  String localUrl;
  WebClient webClient;

  @BeforeEach
  void setUp() {
    localUrl = "http://localhost:" + port;
    webClient = WebClient.builder().baseUrl(localUrl).build();
  }

  @Test
  void testRegisterWithValidRequest_1() {
    final ResponseEntity<String> entity = webClient.post()
        .uri("/register")
        .bodyValue(new UserRegistrationRequestDto("user", "Pass.123", "24.48.0.1"))
        .retrieve()
        .toEntity(String.class)
        .block();
    Assertions.assertEquals(entity.getStatusCode(), HttpStatus.CREATED);
  }

  @Test
  void testRegisterWithValidRequest_2() {
    final ResponseEntity<String> entity = webClient.post()
        .uri("/register")
        .bodyValue(new UserRegistrationRequestDto("user", "Pass#.#123", "24.48.0.1"))
        .retrieve()
        .toEntity(String.class)
        .block();
    Assertions.assertEquals(entity.getStatusCode(), HttpStatus.CREATED);
  }

  @Test
  void testRegisterWithIpAddressOutsideCanada() {
    Assertions.assertThrows(WebClientResponseException.BadRequest.class, () -> webClient.post()
        .uri("/register")
        .bodyValue(new UserRegistrationRequestDto("user", "Pass.123", "14.48.0.1"))
        .retrieve()
        .toEntity(String.class)
        .block());
  }

  @Test
  void testRegisterWithBlankIpAddress() {
    Assertions.assertThrows(WebClientResponseException.BadRequest.class, () -> webClient.post()
        .uri("/register")
        .bodyValue(new UserRegistrationRequestDto("test", "Pass#123", ""))
        .retrieve()
        .toEntity(String.class)
        .block());
  }

  @Test
  void testRegisterWithBlankUsername() {
    Assertions.assertThrows(WebClientResponseException.BadRequest.class, () -> webClient.post()
        .uri("/register")
        .bodyValue(new UserRegistrationRequestDto("", "Pass123", "24.48.0.1"))
        .retrieve()
        .toEntity(String.class)
        .block());
  }

  @Test
  void testRegisterWithBlankPassword() {
    Assertions.assertThrows(WebClientResponseException.BadRequest.class, () -> webClient.post()
        .uri("/register")
        .bodyValue(new UserRegistrationRequestDto("test", "", "24.48.0.1"))
        .retrieve()
        .toEntity(String.class)
        .block());
  }

  @Test
  void testRegisterWithInvalidPassword() {
    Assertions.assertThrows(WebClientResponseException.BadRequest.class, () -> webClient.post()
        .uri("/register")
        .bodyValue(new UserRegistrationRequestDto("user", "Pass123", "24.48.0.1"))
        .retrieve()
        .toEntity(String.class)
        .block());
  }

}