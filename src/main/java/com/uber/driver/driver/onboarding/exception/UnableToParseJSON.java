package com.uber.driver.driver.onboarding.exception;

public class UnableToParseJSON extends RuntimeException{
    public UnableToParseJSON(String message) {
        super(message);
    }
}
