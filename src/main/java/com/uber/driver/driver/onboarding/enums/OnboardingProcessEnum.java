package com.uber.driver.driver.onboarding.enums;

import com.uber.driver.driver.onboarding.exception.InvalidEnumValueException;

import java.util.Arrays;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;

public enum OnboardingProcessEnum {
    DOCUMENT_COLLECTION("document"), VERIFICATION("verification"), SHIP_TRACKING_DEVICE("shipping");
    private String value;

    OnboardingProcessEnum(String value) {
        this.value = value;
    }

    public static OnboardingProcessEnum fromString(String step) {
        for (OnboardingProcessEnum process : OnboardingProcessEnum.values()) {
            if (process.value.equalsIgnoreCase(step)) {
                return process;
            }
        }

        throw new InvalidEnumValueException(
                String.format("Invalid step %s, allowed steps are %s",
                                    step,
                                    Arrays.stream(OnboardingProcessEnum.values()).collect(Collectors.toList())
                )
        );
    }
}
