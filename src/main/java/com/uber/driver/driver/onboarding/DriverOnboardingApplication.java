package com.uber.driver.driver.onboarding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.uber.driver.driver.onboarding"})
public class DriverOnboardingApplication {

	public static void main(String[] args) {
		SpringApplication.run(DriverOnboardingApplication.class, args);
	}

}
