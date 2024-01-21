package com.uber.driver.driver.onboarding.strategy;

import com.uber.driver.driver.onboarding.entity.BackgroundVerification;
import com.uber.driver.driver.onboarding.entity.OnboardData;

public class BackgroundVerificationStrategy extends OnboardingStrategy<BackgroundVerification> {

    public BackgroundVerificationStrategy(BackgroundVerification backgroundVerificationData){
        super(backgroundVerificationData);
    }

    @Override
    protected void executeTask(BackgroundVerification data) {
        System.out.println("BackgroundVerificationStrategy");
    }
}