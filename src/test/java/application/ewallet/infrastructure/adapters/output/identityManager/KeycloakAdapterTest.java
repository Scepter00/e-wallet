package application.ewallet.infrastructure.adapters.output.identityManager;

import application.ewallet.application.output.Identity.IdentityManagerOutputPort;
import application.ewallet.domain.enums.UserRole;
import application.ewallet.domain.enums.constants.IdentityMessages;
import application.ewallet.domain.exceptions.IdentityException;
import application.ewallet.domain.exceptions.WalletException;
import application.ewallet.domain.models.UserIdentity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j
class KeycloakAdapterTest {
    @Autowired
    private IdentityManagerOutputPort identityManagerOutputPort;
    private UserIdentity userIdentity;

    @BeforeEach
    void setUp() {

        userIdentity = UserIdentity.builder()
                .firstName("John")
                .lastName("Johnson")
                .email("john@gmail.com")
                .password("password@123")
                .userRole(UserRole.CUSTOMER)
                .createdAt(LocalDateTime.now().toString())
                .build();
    }

    UserIdentity createJohn() throws WalletException {
        return identityManagerOutputPort.createUser(userIdentity);
    }

    @Test
    void createUser() throws WalletException {
        UserIdentity userIdentity = createJohn();
        assertNotNull(userIdentity);
        assertNotNull(userIdentity.getId());
        assertEquals(userIdentity.getFirstName(), userIdentity.getFirstName());
        assertEquals(userIdentity.getLastName(), userIdentity.getLastName());
        assertEquals(userIdentity.getEmail(), userIdentity.getEmail());
        UserIdentity ebuka = identityManagerOutputPort.findUser(userIdentity);
        assertNotNull( ebuka);
        assertEquals(userIdentity.getId(),ebuka.getId());

    }

    @Test
    void createUserWithNullUserIdentity(){
        assertThrows(WalletException.class,()-> identityManagerOutputPort.createUser(null));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {StringUtils.EMPTY,StringUtils.SPACE})
    void createUserWithEmptyFirstname(String firstname) {
        userIdentity.setFirstName(firstname);
        assertThrows(WalletException.class, ()-> identityManagerOutputPort.createUser(userIdentity));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {StringUtils.SPACE,StringUtils.EMPTY})
    void createUserWithEmptyLastname(String lastName){
        userIdentity.setLastName(lastName);
        assertThrows(WalletException.class, ()-> identityManagerOutputPort.createUser(userIdentity));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {StringUtils.SPACE,StringUtils.EMPTY})
    void createUserWithInvalidPassword(String password){
        userIdentity.setPassword(password);
        assertThrows(WalletException.class, ()-> identityManagerOutputPort.createUser(userIdentity));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {
            StringUtils.SPACE,
            StringUtils.EMPTY,
            "123456",
            "  email@gmail.com",
            "email@gmail.com  ",
            "  email@gmail.com  "
    })
    void creatUserWithValidEmail(String email){
        userIdentity.setEmail(email);
        assertThrows(WalletException.class,() -> identityManagerOutputPort.createUser(userIdentity));
    }

    @Test
    void findUser() throws WalletException {
        UserIdentity ebuka = createJohn();
        UserIdentity userIdentity = identityManagerOutputPort.findUser(ebuka);
        assertNotNull(userIdentity);
        assertNotNull(userIdentity.getId());
        assertEquals(ebuka.getId(),userIdentity.getId());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {
            StringUtils.SPACE,
            StringUtils.EMPTY,
            "123456",
            "  email@gmail.com",
            "email@gmail.com  ",
            "  email@gmail.com  "
    })
    void findUserIdentityWithInvalidEmail(String email){
        userIdentity.setEmail(email);
         assertThrows(IdentityException.class,()-> identityManagerOutputPort.findUser(userIdentity));
    }

    @Test
    void findUserNullUserIdentity() {
        String message = IdentityMessages.USER_NOT_FOUND.getMessage();
        WalletException exception = assertThrows(WalletException.class, () -> identityManagerOutputPort.findUser(null));
        assertEquals(message,exception.getMessage());
    }

    @Test
    void login() throws WalletException {
        UserIdentity ebuka = createJohn();
        UserIdentity tokenResponse = identityManagerOutputPort.login(ebuka);
        assertNotNull(tokenResponse);
        log.info("Access token ---> {}",tokenResponse);
        assertNotNull(tokenResponse.getAccessToken());
        assertNotNull(tokenResponse.getRefreshToken());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {
            StringUtils.SPACE,
            StringUtils.EMPTY,
            "123456",
            "  email@gmail.com",
            "email@gmail.com  ",
            "  email@gmail.com  "
    })
    void loginWithInvalidEmail(String email){
        userIdentity.setEmail(email);
        assertThrows(WalletException.class,()-> identityManagerOutputPort.login(userIdentity));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {
            StringUtils.SPACE,
            StringUtils.EMPTY,
            "123456",
            " 1234Aa@09",
            " 1234A  a@09  ",
            "  1234Aa@09"
    })
    void loginWithInvalidPassword(String password){
        userIdentity.setPassword(password);
        assertThrows(WalletException.class,()-> identityManagerOutputPort.login(userIdentity));
    }

    @Test
    void deleteUser() throws WalletException {
        UserIdentity william = createJohn();
        UserIdentity userIdentity = identityManagerOutputPort.findUser(william);
        william.setId(userIdentity.getId());
        identityManagerOutputPort.deleteUser(william);
        assertThrows(WalletException.class,() ->identityManagerOutputPort.findUser(william));
    }

    @Test
    void deleteUserWithNull()  {
        assertThrows(WalletException.class,() ->identityManagerOutputPort.deleteUser(null));
    }

    @Test
    void deleteUserWithInvalid_Id(){
        userIdentity.setId("invalid");
        assertThrows(WalletException.class,()-> identityManagerOutputPort.deleteUser(userIdentity));
    }

    @ParameterizedTest
    @ValueSource(strings = {StringUtils.SPACE,StringUtils.EMPTY," 22879u9u000878726778  ", "yt766564367645  444"})
    void deleteUserWithInvalidId(String id){
        userIdentity.setId(id);
        assertThrows(WalletException.class,()-> identityManagerOutputPort.deleteUser(userIdentity));
    }

    @AfterEach
    void tearDown(){
        log.info("Cleaning up...");
        try {
            UserIdentity userIdentity1 = identityManagerOutputPort.findUser(userIdentity);
            userIdentity.setId(userIdentity1.getId());
            identityManagerOutputPort.deleteUser(userIdentity);
        }catch (WalletException exception){
            exception.printStackTrace();
        }
    }
}