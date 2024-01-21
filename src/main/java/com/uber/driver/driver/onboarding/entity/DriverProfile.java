package com.uber.driver.driver.onboarding.entity;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class DriverProfile {

    @Id
    protected String id;
    @Indexed(unique = true)
    @NotNull(message = "username cannot be null")
    protected String username;//Unique
    @Field("active")
    protected boolean active = false;
    @Valid
    @NotNull(message = "DriverDetails cannot be null")
    protected DriverDetails driverDetails;
}
