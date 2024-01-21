package com.uber.driver.driver.onboarding.enums;

public enum OnboardingStatusTypes {
    NOT_STARTED(0), IN_PROGRESS(1), FAILED(2), COMPLETED(3);

    private int value;

    OnboardingStatusTypes(int value){
        this.value = value;
    }
}
