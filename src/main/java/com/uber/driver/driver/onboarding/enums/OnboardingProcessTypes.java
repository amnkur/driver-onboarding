package com.uber.driver.driver.onboarding.enums;

import com.uber.driver.driver.onboarding.exception.InvalidEnumValueException;

import java.util.*;


public enum OnboardingProcessTypes {
    DOCUMENT_COLLECTION("document"), VERIFICATION("verification"), SHIP_TRACKING_DEVICE("shipping");
    public String value;

    private static Map<String, OnboardingProcessTypes> map;

    static {
        map = new HashMap<>();
        for (OnboardingProcessTypes process : OnboardingProcessTypes.values()) {
            map.put(process.value, process);
        }
    }

    OnboardingProcessTypes(String value) {
        this.value = value;
    }

    public static OnboardingProcessTypes create(String step) {

        if(map.containsKey(step)) {
            return map.get(step);
        }

        throw new InvalidEnumValueException(
                String.format("Invalid step %s, allowed steps are %s",
                                    step,
                                    map.keySet()
                )
        );
    }
}
