package com.uber.driver.driver.onboarding.services;

import com.uber.driver.driver.onboarding.enums.OnboardingProcessTypes;
import com.uber.driver.driver.onboarding.factory.OnboardingStrategyFactory;
import com.uber.driver.driver.onboarding.strategy.OnboardingStrategy;
import org.springframework.stereotype.Service;


@Service
public class OnboardingServiceImpl implements OnboardingService{

    @Override
    public void initiateOnboarding(String payload, String username, OnboardingProcessTypes process) {
        OnboardingStrategy onboardingStrategy = OnboardingStrategyFactory.createStrategy(process);
        onboardingStrategy.execute(payload);
    }
}
