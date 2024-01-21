package com.uber.driver.driver.onboarding.entity;

import com.uber.driver.driver.onboarding.enums.OnboardingStatusTypes;
import lombok.Data;

@Data
public class BackgroundVerification implements OnboardData {
    private Address nativeAddress;
    private int adharNumber;
    private String panCard;
    private OnboardingStatusTypes status;
}
