package com.uber.driver.driver.onboarding.resources;

import com.uber.driver.driver.onboarding.constants.Constants;
import com.uber.driver.driver.onboarding.entity.SecureDriverProfile;
import com.uber.driver.driver.onboarding.enums.OnboardingProcessTypes;
import com.uber.driver.driver.onboarding.services.DriverProfileService;
import com.uber.driver.driver.onboarding.services.OnboardingService;
import com.uber.driver.driver.onboarding.util.ValidationUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/driver")
public class DriverResources {

    private final DriverProfileService driverProfileService;
    private final OnboardingService onboardingService;

    @Autowired
    public DriverResources(DriverProfileService driverProfileService, OnboardingService onboardingService) {
        this.driverProfileService = driverProfileService;
        this.onboardingService = onboardingService;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody @Valid SecureDriverProfile driverProfile) {
        driverProfileService.createDriverProfile(driverProfile);
        return ResponseEntity.ok("Driver profile created successfully");
    }

    @PostMapping(value = "/onboard")
    public ResponseEntity<String> initiateOnboard(@RequestParam(name = Constants.USERNAME) String username,
                                                  @RequestParam(name = Constants.ONBOARD_STEP) String onboardStep,
                                                  @RequestBody(required = false) String payload) {


        ValidationUtil.validateData(Pair.of(Constants.USERNAME, username),
                                        Pair.of(Constants.ONBOARD_STEP, onboardStep),
                                                Pair.of(Constants.PAYLOAD, payload)
        );
        onboardingService.initiateOnboarding(payload, username, OnboardingProcessTypes.create(onboardStep));
        return ResponseEntity.ok("Onboarding initiated successfully");
    }

    @PatchMapping("/ready")
    public ResponseEntity<String> markReady(@RequestParam @NotBlank String username,
                                            @RequestParam(name = "ready", defaultValue = "true") boolean ready) {

        driverProfileService.updateDriverStatus(username, ready);
        return ResponseEntity.ok("Driver marked as " + (ready ? "ready" : "not ready"));
    }
}
