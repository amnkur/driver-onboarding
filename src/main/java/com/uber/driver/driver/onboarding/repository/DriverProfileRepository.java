package com.uber.driver.driver.onboarding.repository;

import com.uber.driver.driver.onboarding.entity.DriverProfile;
import com.uber.driver.driver.onboarding.entity.SecureDriverProfile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverProfileRepository extends MongoRepository<SecureDriverProfile, String> {

    DriverProfile findByUsername(String username);


}
