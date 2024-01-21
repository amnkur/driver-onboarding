package com.uber.driver.driver.onboarding.entity;

import com.uber.driver.driver.onboarding.enums.OnboardingStatusTypes;
import lombok.Data;

@Data
public class Shipping implements OnboardData{
    private Address shippingAddress;
    private Address billingAddress;
    private OnboardingStatusTypes status;
}

