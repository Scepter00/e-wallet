package application.ewallet.infrastructure.adapters.input.controllers;

import application.ewallet.application.input.CreateUserUseCase;
import application.ewallet.domain.enums.UserRole;
import application.ewallet.domain.exceptions.WalletException;
import application.ewallet.domain.models.UserIdentity;
import application.ewallet.infrastructure.adapters.input.data.request.UserIdentityRequest;
import application.ewallet.infrastructure.adapters.input.mappers.IdentityMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@Slf4j
@WebMvcTest(controllers = IdentityManagerController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class IdentityManagerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private CreateUserUseCase createUserUseCase;

    @MockBean
    private IdentityMapper identityMapper;

    private UserIdentityRequest userIdentityRequest;

    private UserIdentity userIdentity;

    @BeforeEach
    void setUp() throws WalletException {
        userIdentity = UserIdentity.builder()
                .id("3dc5ab57-1da0-4567-82fa-073cc05af4ce")
                .firstName("John")
                .lastName("Doe")
                .email("doe@gmail.com")
                .phoneNumber("08012345678")
                .password("Password@123")
                .userRole(UserRole.CUSTOMER)
                .createdAt(LocalDateTime.now().toString())
                .build();

        userIdentityRequest = UserIdentityRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .email("doe@gmail.com")
                .password("Password@123")
                .userRole(UserRole.CUSTOMER)
                .build();

        given(identityMapper.mapUserIdentityRequestToUserIdentity(ArgumentMatchers.any())).willReturn(userIdentity);
        given(createUserUseCase.signup(ArgumentMatchers.any())).willReturn(userIdentity);
    }

    @Test
    void signUp() throws Exception {
        ResultActions response = mockMvc.perform(post("/api/v1/signup/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userIdentityRequest)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.email", CoreMatchers.is(userIdentityRequest.getEmail())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.firstName", CoreMatchers.is(userIdentityRequest.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.lastName", CoreMatchers.is(userIdentityRequest.getLastName())));
        log.info("Response: =============> {}", response.andReturn().getResponse().getContentAsString());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {StringUtils.EMPTY, StringUtils.SPACE})
    void signUpWithInvalidEmail(String email) throws Exception {
        userIdentityRequest.setEmail(email);
        ResultActions response = mockMvc.perform(post("/api/v1/signup/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userIdentityRequest)));

        response.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {StringUtils.EMPTY, StringUtils.SPACE})
    void signUpWithInvalidFirstName(String firstName) throws Exception {
        userIdentityRequest.setFirstName(firstName);
        ResultActions response = mockMvc.perform(post("/api/v1/signup/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userIdentityRequest)));

        response.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {StringUtils.EMPTY, StringUtils.SPACE})
    void signUpWithInvalidLastName(String lastName) throws Exception {
        userIdentityRequest.setLastName(lastName);
        ResultActions response = mockMvc.perform(post("/api/v1/signup/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userIdentityRequest)));

        response.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {StringUtils.EMPTY, StringUtils.SPACE})
    void signUpWithInvalidPassword(String password) throws Exception {
        userIdentityRequest.setPassword(password);
        ResultActions response = mockMvc.perform(post("/api/v1/signup/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userIdentityRequest)));

        response.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void signUpWithNullRole() throws Exception {
        userIdentityRequest.setUserRole(null);
        ResultActions response = mockMvc.perform(post("/api/v1/signup/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userIdentityRequest)));

        response.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void findUserById() throws Exception {
        given(createUserUseCase.findUserById(ArgumentMatchers.anyString())).willReturn(userIdentity);
        ResultActions response = mockMvc.perform(get("/api/v1/search/user/3dc5ab57-1da0-4567-82fa-073cc05af4ce"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.email", CoreMatchers.is(userIdentity.getEmail())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.firstName", CoreMatchers.is(userIdentity.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.lastName", CoreMatchers.is(userIdentity.getLastName())));
    }

    @Test
    void login() throws Exception {
        given(createUserUseCase.login(ArgumentMatchers.any())).willReturn(userIdentity);
        ResultActions response = mockMvc.perform(post("/api/v1/login/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userIdentityRequest)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.email", CoreMatchers.is(userIdentity.getEmail())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.firstName", CoreMatchers.is(userIdentity.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.lastName", CoreMatchers.is(userIdentity.getLastName())));
        log.info("Response: ============> {}", response.andReturn().getResponse().getContentAsString());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {StringUtils.EMPTY, StringUtils.SPACE})
    void loginWithInvalidEmail(String email) throws Exception {
        userIdentityRequest.setEmail(email);
        ResultActions response = mockMvc.perform(post("/api/v1/login/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userIdentityRequest)));

        response.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {StringUtils.EMPTY, StringUtils.SPACE})
    void loginWithInvalidPassword(String password) throws Exception {
        userIdentityRequest.setPassword(password);
        ResultActions response = mockMvc.perform(post("/api/v1/login/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userIdentityRequest)));

        response.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void deleteUser() throws Exception {
        ResultActions response = mockMvc.perform(delete("/api/v1/delete/3dc5ab57-1da0-4567-82fa-073cc05af4ce"));

        response.andExpect(MockMvcResultMatchers.status().isOk());
        log.info("Deleted user: ============> {}", response.andReturn().getResponse().getContentAsString());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {StringUtils.EMPTY, StringUtils.SPACE})
    void deleteUserWithInvalidId(String id) throws Exception {
        userIdentityRequest.setId(id);
        ResultActions response = mockMvc.perform(delete("/api/v1/delete/3dc5ab57-1da0-4567-82fa-073cc05af4ce"));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }
}