package com.uber.driver.driver.onboarding.entity;

import com.uber.driver.driver.onboarding.enums.OnboardingStatusTypes;
import lombok.Data;

@Data
public class DocumentCollection implements OnboardData {
    private Document[] documents;
    private OnboardingStatusTypes status;
}

