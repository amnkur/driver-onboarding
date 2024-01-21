package com.uber.driver.driver.onboarding.strategy;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.uber.driver.driver.onboarding.entity.OnboardData;
import com.uber.driver.driver.onboarding.exception.UnableToParseJSON;

import java.io.IOException;

public abstract class OnboardingStrategy<T extends OnboardData> {
    private ObjectMapper objectMapper = new ObjectMapper();
    OnboardData data;

    public OnboardingStrategy(OnboardData data){
        this.data = data;
    }
    public void execute(String payload) {
       T d = (T) loadData(payload, data);
       executeTask(d);
    }

    protected abstract void executeTask(T data);

    private Object loadData(String payload, OnboardData data) {
        try {
            return objectMapper.readValue(payload, data.getClass());
        } catch (IOException e) {
            String message = "";
            if(e instanceof UnrecognizedPropertyException) {
                UnrecognizedPropertyException ue = (UnrecognizedPropertyException) e;
                message = String.format(": invalid property is [%s],%s", ue.getPropertyName(), ue.getMessageSuffix());
            }
            if(e instanceof InvalidFormatException) {
                InvalidFormatException ie = (InvalidFormatException) e;
                message = String.format(": %s", ie.getMessage());
            }
            if(e instanceof JsonParseException) {
                JsonParseException je = (JsonParseException) e;
                message = String.format(": %s", je.getMessage());
            }
            throw new UnableToParseJSON("Unable to parse request json" + message);
        }
    }
}