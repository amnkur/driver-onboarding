package com.uber.driver.driver.onboarding.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "driver_active")
@Data
public class ActiveDriver {
    String driverId;
}
