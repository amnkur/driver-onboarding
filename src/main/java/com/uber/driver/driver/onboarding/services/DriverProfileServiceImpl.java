package com.uber.driver.driver.onboarding.services;

import com.uber.driver.driver.onboarding.entity.DriverProfile;
import com.uber.driver.driver.onboarding.entity.SecureDriverProfile;
import com.uber.driver.driver.onboarding.exception.DetailsNotFoundException;
import com.uber.driver.driver.onboarding.exception.UsernameDuplicateException;
import com.uber.driver.driver.onboarding.repository.DriverProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DriverProfileServiceImpl implements DriverProfileService {

    @Autowired
    private DriverProfileRepository driverProfileRepository;

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Override
    public void createDriverProfile(SecureDriverProfile driverProfile) {
        
        if (!driverExist(driverProfile.getUsername())) {
            driverProfile.setPassword(passwordEncoder.encode(driverProfile.getPassword()));
            driverProfileRepository.save(driverProfile);
        } else {
            throw new UsernameDuplicateException("Driver username, already Signed up");
        }
    }

    private boolean driverExist(String uName) {
        Optional<DriverProfile> existingTask = Optional.ofNullable(driverProfileRepository.findByUsername(uName));
        return existingTask.isPresent();
    }

    @Override
    public DriverProfile getDriverProfile(String name) {
        return driverProfileRepository.findByUsername(name);
    }

    public void updateDriverStatus(String username, boolean active){

        Query query = new Query(Criteria.where("username").is(username));
        Update update = new Update().set("active", active);
        FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true);
        mongoTemplate.findAndModify(query, update, options, SecureDriverProfile.class);
    }
}