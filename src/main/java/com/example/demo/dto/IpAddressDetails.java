package com.example.demo.dto;

import lombok.Data;

@Data
public class IpAddressDetails {
  String query;
  String status;
  String country;
  String countryCode;
  String region;
  String regionName;
  String city;
  String zip;
  Double lat;
  Double lon;
  String timezone;
  String isp;
  String org;
  String as;
}
