package com.uber.driver.driver.onboarding.factory;

import com.uber.driver.driver.onboarding.entity.BackgroundVerification;
import com.uber.driver.driver.onboarding.entity.DocumentCollection;
import com.uber.driver.driver.onboarding.entity.Shipping;
import com.uber.driver.driver.onboarding.enums.OnboardingProcessTypes;
import com.uber.driver.driver.onboarding.strategy.BackgroundVerificationStrategy;
import com.uber.driver.driver.onboarding.strategy.DocumentCollectionStrategy;
import com.uber.driver.driver.onboarding.strategy.OnboardingStrategy;
import com.uber.driver.driver.onboarding.strategy.ShippingStrategy;

public class OnboardingStrategyFactory {

    public static OnboardingStrategy createStrategy(OnboardingProcessTypes step) {
        switch (step) {
            case DOCUMENT_COLLECTION:
                return new DocumentCollectionStrategy(new DocumentCollection());
            case VERIFICATION:
                return new BackgroundVerificationStrategy(new BackgroundVerification());
            case SHIP_TRACKING_DEVICE:
                return new ShippingStrategy(new Shipping());
            default:
                throw new IllegalArgumentException("Invalid onboarding step: " + step);
        }
    }

    public static void test(){}
}