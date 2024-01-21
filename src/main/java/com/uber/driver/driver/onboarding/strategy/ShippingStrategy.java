package com.uber.driver.driver.onboarding.strategy;

import com.uber.driver.driver.onboarding.entity.Shipping;

public class ShippingStrategy extends OnboardingStrategy<Shipping> {

    public ShippingStrategy(Shipping shippingData){
        super(shippingData);
    }
    @Override
    public void executeTask(Shipping data) {
        // Logic for shipping tracking device
        System.out.println("Executing Shipping Strategy");
    }
}