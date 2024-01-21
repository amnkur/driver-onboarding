package com.uber.driver.driver.onboarding.entity;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "driver_profile")
public class SecureDriverProfile extends DriverProfile {

    @Pattern(regexp = "^(?=.*[a-zA-Z0-9])(?=.*[@#$%^&+=])(?=\\S+$).{8,15}$",
            message = "Password must be at least 8 characters long and less than 15 characters and contain at least one special character")
    @NotNull(message = "password cannot be null")
    protected String password;
}
