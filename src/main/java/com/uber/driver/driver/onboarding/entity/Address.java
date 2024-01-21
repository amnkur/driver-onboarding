package com.uber.driver.driver.onboarding.entity;

import lombok.Data;

@Data
public class Address {
    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;
}