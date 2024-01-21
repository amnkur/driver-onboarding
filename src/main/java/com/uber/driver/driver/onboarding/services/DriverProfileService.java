package com.uber.driver.driver.onboarding.services;

import com.uber.driver.driver.onboarding.entity.DriverProfile;
import com.uber.driver.driver.onboarding.entity.SecureDriverProfile;
import com.uber.driver.driver.onboarding.exception.DetailsNotFoundException;
import org.springframework.stereotype.Service;

@Service
public interface DriverProfileService {
    void createDriverProfile(SecureDriverProfile driverProfile);
    DriverProfile getDriverProfile(String driverId) throws DetailsNotFoundException;
    void updateDriverStatus(String username, boolean active);
}