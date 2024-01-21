package com.uber.driver.driver.onboarding.repository;

import com.uber.driver.driver.onboarding.entity.DriverOnboarding;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OnboardingRepository extends MongoRepository<DriverOnboarding, String> {
}
