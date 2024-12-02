package application.ewallet.domain.services;

import application.ewallet.application.input.CreateWalletUseCase;
import application.ewallet.application.output.Identity.IdentityManagerOutputPort;
import application.ewallet.application.output.Identity.UserIdentityOutputPort;
import application.ewallet.domain.enums.UserRole;
import application.ewallet.domain.exceptions.WalletException;
import application.ewallet.domain.models.UserIdentity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
class UserIdentityServiceTest {

    @InjectMocks
    private UserIdentityService userIdentityService;

    @Mock
    private UserIdentityOutputPort userIdentityOutputPort;

    @Mock
    private IdentityManagerOutputPort identityManagerOutPutPort;

    @Mock
    private CreateWalletUseCase createWalletUseCase;

    private UserIdentity userIdentity;


    @BeforeEach
    void setUp() {
        userIdentity = new UserIdentity();
        userIdentity.setId("4224a5ca-780b-413c-8cf4-85125d6ce7d8");
        userIdentity.setFirstName("John");
        userIdentity.setLastName("Johnson");
        userIdentity.setEmail("john@gmail.com");
        userIdentity.setUserRole(UserRole.CUSTOMER);
        userIdentity.setPassword("password@123");
    }


    @Test
    void signUp() throws WalletException {
        when(identityManagerOutPutPort.createUser(any(UserIdentity.class))).thenReturn(userIdentity);
        when(userIdentityOutputPort.save(any(UserIdentity.class))).thenReturn(userIdentity);
        UserIdentity savedUser = userIdentityService.signup(userIdentity);
        assertNotNull(savedUser);
        assertEquals(userIdentity.getId(), savedUser.getId());
        assertEquals(userIdentity.getEmail(), savedUser.getEmail());
        assertEquals(userIdentity.getFirstName(), savedUser.getFirstName());
        assertEquals(userIdentity.getLastName(), savedUser.getLastName());
    }


    @Test
    void signUpWithNullUser() {
        assertThrows(Exception.class, () -> userIdentityService.signup(null));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {StringUtils.EMPTY, StringUtils.SPACE})
    void signUpWithInvalidEmail(String email) {
        userIdentity.setEmail(email);
        assertThrows(Exception.class, () -> userIdentityService.signup(userIdentity));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {StringUtils.EMPTY, StringUtils.SPACE})
    void signUpWithInvalidPassword(String password) {
        userIdentity.setPassword(password);
        assertThrows(Exception.class, () -> userIdentityService.signup(userIdentity));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {StringUtils.EMPTY, StringUtils.SPACE})
    void signUpWithInvalidFirstName(String firstName) {
        userIdentity.setFirstName(firstName);
        assertThrows(Exception.class, () -> userIdentityService.signup(userIdentity));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {StringUtils.EMPTY, StringUtils.SPACE})
    void signUpWithNullLastName(String lastName) {
        userIdentity.setLastName(lastName);
        assertThrows(Exception.class, () -> userIdentityService.signup(userIdentity));
    }

    @Test
    void signUpWithNullUserRole() {
        userIdentity.setUserRole(null);
        assertThrows(Exception.class, () -> userIdentityService.signup(userIdentity));
    }

    @Test
    void findUser() throws WalletException {
        when(identityManagerOutPutPort.findUser(userIdentity)).thenReturn(userIdentity);
        when(userIdentityOutputPort.findById(userIdentity.getId())).thenReturn(userIdentity);
        UserIdentity foundUser = userIdentityService.findUser(userIdentity);
        assertNotNull(foundUser);
        assertEquals(userIdentity.getId(), foundUser.getId());
        assertEquals(userIdentity.getEmail(), foundUser.getEmail());
        assertEquals(userIdentity.getFirstName(), foundUser.getFirstName());
        assertEquals(userIdentity.getLastName(), foundUser.getLastName());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {StringUtils.EMPTY, StringUtils.SPACE})
    void findUserWithInvalidEmail(String id) {
        userIdentity.setId(id);
        assertThrows(Exception.class, () -> userIdentityService.findUser(userIdentity));
    }

    @Test
    void login() throws WalletException {
        when(identityManagerOutPutPort.login(userIdentity)).thenReturn(userIdentity);
        UserIdentity loggedInUser = userIdentityService.login(userIdentity);
        assertNotNull(loggedInUser);
        assertEquals(userIdentity.getId(), loggedInUser.getId());
        assertEquals(userIdentity.getEmail(), loggedInUser.getEmail());
        assertEquals(userIdentity.getFirstName(), loggedInUser.getFirstName());
        assertEquals(userIdentity.getLastName(), loggedInUser.getLastName());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {StringUtils.EMPTY, StringUtils.SPACE})
    void loginWithInvalidEmail(String email) {
        userIdentity.setEmail(email);
        assertThrows(Exception.class, () -> userIdentityService.login(userIdentity));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {StringUtils.EMPTY, StringUtils.SPACE})
    void loginWithInvalidPassword(String password) {
        userIdentity.setPassword(password);
        assertThrows(Exception.class, () -> userIdentityService.login(userIdentity));
    }

    @Test
    void deleteUser() throws WalletException {
        when(userIdentityOutputPort.findById(userIdentity.getId())).thenReturn(userIdentity);
        userIdentityService.deleteUser(userIdentity);
        verify(identityManagerOutPutPort).deleteUser(userIdentity);
        verify(userIdentityOutputPort).deleteUserById(userIdentity.getId());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {StringUtils.EMPTY, StringUtils.SPACE})
    void deleteUserWithInvalidEmail(String email) {
        userIdentity.setEmail(email);
        assertThrows(Exception.class, () -> userIdentityService.deleteUser(userIdentity));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {StringUtils.EMPTY, StringUtils.SPACE})
    void deleteUserWithInvalidId(String id) {
        userIdentity.setId(id);
        assertThrows(Exception.class, () -> userIdentityService.deleteUser(userIdentity));
    }
}
