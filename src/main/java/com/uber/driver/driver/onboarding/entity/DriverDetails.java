package com.uber.driver.driver.onboarding.entity;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

@Data
@Builder
public class DriverDetails {

    @Id
    private String id;
    @NotNull(message = "Name cannot be null")
    private String name;
    @NotNull(message = "Contact Details cannot be null")
    private String contactDetails;
    @Indexed(unique = true)
    @NotNull(message = "License cannot be null")
    private String licenseDetails;//Unique

}
