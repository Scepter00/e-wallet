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
        userIdentity.setFirstName("John");
        userIdentity.setLastName("Johnson");
        userIdentity.setEmail("sam@gmail.com");
        userIdentity.setUserRole(UserRole.CUSTOMER);
        userIdentity.setPassword("password@123");
    }


    @Test
    void signUp() throws WalletException {
        when(identityManagerOutPutPort.createUser(any(UserIdentity.class))).thenReturn(userIdentity);
        when(userIdentityOutputPort.save(any(UserIdentity.class))).thenReturn(userIdentity);
        UserIdentity savedUser = userIdentityService.signup(userIdentity);
        assertNotNull(savedUser);
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
    void deleteUser() throws WalletException {
        when(userIdentityOutputPort.findById(userIdentity.getId())).thenReturn(userIdentity);
        userIdentityService.deleteUser(userIdentity);
        verify(identityManagerOutPutPort).deleteUser(userIdentity);
        verify(userIdentityOutputPort).deleteUserById(userIdentity.getId());
    }

    @Test
    void deleteUserWithNullRole() {
        userIdentity.setUserRole(null);
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
