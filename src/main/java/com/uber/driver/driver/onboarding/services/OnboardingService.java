package com.uber.driver.driver.onboarding.services;

import com.uber.driver.driver.onboarding.enums.OnboardingProcessTypes;

public interface OnboardingService {
    void initiateOnboarding(String paylaod, String username, OnboardingProcessTypes process);
}