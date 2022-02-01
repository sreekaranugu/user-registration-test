package com.example.demo.service;

import com.example.demo.dto.IpAddressDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class IpVerificationService {

  private final WebClient webClient = WebClient.builder().baseUrl("http://ip-api.com").build();

  public IpAddressDetails getIpAddressDetails(String ipAddress) {
    final IpAddressDetails ipAddressDetails = webClient.get()
        .uri("/json/" + ipAddress)
        .retrieve()
        .bodyToMono(IpAddressDetails.class)
        .block();
    return ipAddressDetails;
  }

}
