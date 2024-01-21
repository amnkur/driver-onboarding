package com.uber.driver.driver.onboarding.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.uber.driver.driver.onboarding.enums.OnboardingProcessTypes;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class OnboardingData {
    @NonNull
    private String driverId;
    private OnboardingProcessTypes process;
    private JsonNode processDetails;
}
