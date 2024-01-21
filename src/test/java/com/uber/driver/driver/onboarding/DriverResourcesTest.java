package com.uber.driver.driver.onboarding.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uber.driver.driver.onboarding.constants.Constants;
import com.uber.driver.driver.onboarding.entity.DocumentCollection;
import com.uber.driver.driver.onboarding.entity.DriverDetails;
import com.uber.driver.driver.onboarding.entity.SecureDriverProfile;
import com.uber.driver.driver.onboarding.enums.OnboardingProcessTypes;
import com.uber.driver.driver.onboarding.repository.DriverProfileRepository;
import com.uber.driver.driver.onboarding.services.DriverProfileServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.containsString;


@WebMvcTest(value = DriverResources.class)
@ComponentScan(basePackages = "com.uber.driver.driver.onboarding")
public class DriverResourcesTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DriverProfileRepository driverProfileRepository;

    @MockBean
    private MongoTemplate mongoTemplate;

    @Autowired
    private DriverProfileServiceImpl driverProfileService;
    @Autowired
    private ObjectMapper objectMapper;

    private final String READY_URI = "/api/driver/ready";
    private final String SIGNUP_URI = "/api/driver/signup";
    private final String ONBOARD_URI = "/api/driver/onboard";
    private final String READY_CONTENT = "Driver marked as ready";
    private final String NOT_READY_CONTENT = "Driver marked as not ready";
    private final String USERNAME_PARAM = "username";
    private final String READY_PARAM = "ready";

    @Test
    public void testSignUp_Positive() throws Exception {
        SecureDriverProfile validDriverProfile = SecureDriverProfile.builder()
                .active(false)
                .username("testUser")
                .password("testPass@")
                .driverDetails(DriverDetails.builder()
                        .name("test User")
                        .contactDetails("user Address")
                        .licenseDetails("xyz123")
                        .build())
                .build();

        String jsonPayload = objectMapper.writeValueAsString(validDriverProfile);
        System.out.println("Generated JSON: " + jsonPayload);

        mockMvc.perform(MockMvcRequestBuilders.post(SIGNUP_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validDriverProfile)))
                .andExpect(status().isOk());
    }

    @Test
    public void testSignUp_passwordLength() throws Exception {
        SecureDriverProfile validDriverProfile = SecureDriverProfile.builder()
                .active(false)
                .username("testUser")
                .password("test")
                .driverDetails(DriverDetails.builder()
                        .name("test User")
                        .contactDetails("user Address")
                        .licenseDetails("xyz123")
                        .build())
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post(SIGNUP_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validDriverProfile)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string(
                        containsString("Password must be at least 8 characters long and less than 15 characters and contain at least one special character")));
    }

    @Test
    public void testSignUp_Negative_NullProfile() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(SIGNUP_URI)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testSignUp_Negative_BlankUsername() throws Exception {
        SecureDriverProfile blankUsernameProfile = SecureDriverProfile.builder()
                .active(false)
                .username("")
                .password("testPass")
               .driverDetails(DriverDetails.builder()
                        .contactDetails("user Address")
                        .licenseDetails("xyz123")
                        .build())
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post(SIGNUP_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(blankUsernameProfile)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testSignUp_Negative_BlankPassword() throws Exception {
        SecureDriverProfile blankPasswordProfile = SecureDriverProfile.builder()
                .active(false)
                .username("")
                .password("")
                .driverDetails(DriverDetails.builder()
                        .contactDetails("user Address")
                        .licenseDetails("xyz123")
                        .build())
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post(SIGNUP_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(blankPasswordProfile)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test_signup_user_exist() throws Exception {
        String user = "testUser";
        SecureDriverProfile userProfile = SecureDriverProfile.builder()
                .active(false)
                .username(user)
                .password("testPass@")
                .driverDetails(DriverDetails.builder()
                        .name("Test User")
                        .contactDetails("user Address")
                        .licenseDetails("xyz123")
                        .build())
                .build();

        Mockito.when(driverProfileRepository.findByUsername(user)).thenReturn(userProfile);
        mockMvc.perform(MockMvcRequestBuilders.post(SIGNUP_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userProfile)))
                .andExpect(status().isConflict())
                .andExpect(MockMvcResultMatchers.content().string("Driver username, already Signed up"));
    }

    @Test
    void test_onboard() throws Exception {
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(ONBOARD_URI)
                .param(Constants.USERNAME, "testUser")
                .param(Constants.ONBOARD_STEP, OnboardingProcessTypes.DOCUMENT_COLLECTION.value)
                        .content(objectMapper.writeValueAsString(new DocumentCollection()))
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk());
        resultActions.andExpect(MockMvcResultMatchers.content().string("Onboarding initiated successfully"));

    }

    @Test
    void test_onboard_invalidOnboardingStep() throws Exception {
        String step = "testStep";
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(ONBOARD_URI)
                .param(Constants.USERNAME, "testUser")
                .param(Constants.ONBOARD_STEP, step)
                .content(objectMapper.writeValueAsString(new DocumentCollection()))
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isBadRequest());
        resultActions.andExpect(MockMvcResultMatchers.content().string("Invalid step " + step + ", allowed steps are [shipping, document, verification]"));

    }

    @Test
    void test_onboard_invalidPayload() throws Exception {
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(ONBOARD_URI)
                .param(Constants.USERNAME, "testUser")
                .param(Constants.ONBOARD_STEP, OnboardingProcessTypes.DOCUMENT_COLLECTION.value)
                .content("{'testAttr': 'test value'}")
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isBadRequest());
        resultActions.andExpect(MockMvcResultMatchers.content().string(containsString("Unable to parse request json")));

    }

    @Test
    void test_mark_ready_blankUsername() throws Exception {
        boolean ready = true;

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.patch(READY_URI)
                        .param(READY_PARAM, String.valueOf(ready))
                        .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isBadRequest());

    }

    @Test
    void testMarkReadyEndpoint() throws Exception {
        String username = "testUser";
        boolean ready = true;

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.patch(READY_URI)
                        .param(USERNAME_PARAM, username)
                        .param(READY_PARAM, String.valueOf(ready))
                        .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(READY_CONTENT));

    }

    @Test
    void testMarkReadyEndpointDriverNotReady() throws Exception {
        String username = "testUser";
        boolean ready = false;

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.patch(READY_URI)
                        .param(USERNAME_PARAM, username)
                        .param(READY_PARAM, String.valueOf(ready))
                        .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(NOT_READY_CONTENT));

    }

    @Test
    void testMarkReadyEndpointInvalidReadyParam() throws Exception {
        String username = "testUser";

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.patch(READY_URI)
                        .param(USERNAME_PARAM, username)
                        .param(READY_PARAM, "invalidReady")
                        .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isBadRequest())
        .andExpect(MockMvcResultMatchers
                .content()
                .string("Invalid value for 'ready'. Please provide a valid boolean value.")
        );

    }
}
