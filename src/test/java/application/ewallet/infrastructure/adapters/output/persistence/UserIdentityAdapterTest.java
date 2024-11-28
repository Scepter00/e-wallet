package application.ewallet.infrastructure.adapters.output.persistence;

import application.ewallet.application.output.Identity.UserIdentityOutputPort;
import application.ewallet.domain.enums.UserRole;
import application.ewallet.domain.exceptions.IdentityException;
import application.ewallet.domain.exceptions.WalletException;
import application.ewallet.domain.models.UserIdentity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
class UserIdentityAdapterTest {
    @Autowired
    private UserIdentityOutputPort userIdentityOutputPort;
    private UserIdentity userIdentity;
    private UserIdentity userIdentity2;

    @BeforeEach
    void setUp () {
        userIdentity = new UserIdentity();
        userIdentity2 = new UserIdentity();
        userIdentity.setId("2c521790-563a-4449-a4bd-459bd5a2d4d7");
        userIdentity.setFirstName("Ebuka");
        userIdentity.setLastName("Chukwunenye");
        userIdentity.setEmail("johnson@example.com");
        userIdentity.setPhoneNumber("08164668725");
        userIdentity.setUserRole(UserRole.CUSTOMER);
        userIdentity.setCreatedAt(LocalDateTime.now().toString());
        userIdentity.setEmailVerified(false);
        userIdentity.setCreatedBy("2c521790-563a-4449-a4bd-459bd5a2d4d7");
    }

    UserIdentity createUser() throws WalletException {
        return userIdentityOutputPort.save(userIdentity);
    }


    @Test
    void saveUserWithExistingEmail() throws WalletException {
            UserIdentity savedJohn = userIdentityOutputPort.save(userIdentity);
            assertEquals(userIdentity.getId(),savedJohn.getId());
    }

    @Test
    void saveUserWithNullUserIdentity(){
        assertThrows(WalletException.class, ()->userIdentityOutputPort.save(null));
    }

    @Test
    void saveUserWithNullEmail(){
        userIdentity.setEmail(null);
        assertThrows(WalletException.class, ()->userIdentityOutputPort.save(userIdentity));
    }

    @Test
    void saveUserWithEmptyEmail(){
        userIdentity.setEmail(StringUtils.EMPTY);
        assertThrows(WalletException.class, ()->userIdentityOutputPort.save(userIdentity));
    }

    @Test
    void saveUserWithInvalidEmail(){
        userIdentity.setEmail("invalid");
        assertThrows(WalletException.class, ()->userIdentityOutputPort.save(userIdentity));
    }

    @Test
    void saveUserWithNullFirstName(){
        userIdentity.setFirstName(null);
        assertThrows(WalletException.class, ()->userIdentityOutputPort.save(userIdentity));
    }
    @Test
    void saveUserWithEmptyFirstName(){
        userIdentity.setFirstName(StringUtils.EMPTY);
        assertThrows(WalletException.class, ()->userIdentityOutputPort.save(userIdentity));
    }
    @Test
    void saveUserWithNullLastName(){
        userIdentity.setLastName(null);
        assertThrows(WalletException.class, ()->userIdentityOutputPort.save(userIdentity));
    }
    @Test
    void saveUserWithEmptyLastName(){
        userIdentity.setLastName(StringUtils.EMPTY);
        assertThrows(WalletException.class, ()->userIdentityOutputPort.save(userIdentity));
    }

    @Test
    void saveUserWithNullRole() {
        userIdentity.setUserRole(null);
        assertThrows(WalletException.class, ()->userIdentityOutputPort.save(userIdentity));
    }

    @Test
    void saveUserWithNullCreatedBy() {
        userIdentity.setCreatedBy(null);
        assertThrows(WalletException.class, ()->userIdentityOutputPort.save(userIdentity));
    }

    @Test
    void saveUserWithEmptyCreatedBy() {
        userIdentity.setCreatedBy(StringUtils.EMPTY);
        assertThrows(WalletException.class, ()->userIdentityOutputPort.save(userIdentity));
    }

    @Test
    void saveUser() throws WalletException {
        UserIdentity user = createUser();
        assertNotNull(user);
        assertNotNull(user.getId());
        UserIdentity foundUser = userIdentityOutputPort.save(userIdentity);
        assertEquals(foundUser.getFirstName(),user.getFirstName());
        assertEquals(foundUser.getLastName(),user.getLastName());
        assertEquals(foundUser.getCreatedBy(),user.getCreatedBy());
    }

    @Test
    void deleteUserWithNullEmail() {
        userIdentity.setEmail(null);
        assertThrows(WalletException.class,()->userIdentityOutputPort.deleteUserByEmail(userIdentity.getEmail()));
    }

    @Test
    void deleteUserWithEmptyEmail() {
        userIdentity.setEmail(StringUtils.EMPTY);
        assertThrows(WalletException.class,()->userIdentityOutputPort.deleteUserByEmail(userIdentity.getEmail()));
    }

    @Test
    void deleteUserWithNonExistingEmail() {
        userIdentity.setEmail("email@gmail.com");
        assertThrows(IdentityException.class,()->userIdentityOutputPort.deleteUserByEmail(userIdentity.getEmail()));
    }

    @Test
    void deleteUserWithInvalidEmailFormat() {
        userIdentity.setEmail("email@email.com");
        assertThrows(WalletException.class,()->userIdentityOutputPort.deleteUserByEmail(userIdentity.getEmail()));
    }

    @Test
    void deleteUserWithUserId() throws WalletException {
            UserIdentity existingUser = userIdentityOutputPort.findById(userIdentity.getId());
            assertEquals(existingUser.getId(),userIdentity.getId());
            userIdentityOutputPort.deleteUserById(userIdentity.getId());
        assertThrows(IdentityException.class,()-> userIdentityOutputPort.findById(userIdentity.getId()));
    }

    @Test
    void deleteUserWithEmptyId() {
        userIdentity.setId(StringUtils.EMPTY);
        assertThrows(WalletException.class,()->userIdentityOutputPort.deleteUserById(userIdentity.getId()));
    }

    @Test
    void deleteUserWithNullId() {
        userIdentity.setId(null);
        assertThrows(WalletException.class,()->userIdentityOutputPort.deleteUserById(userIdentity.getId()));
    }

    @Test
    void deleteUserWithNonExistingUserId() {
        userIdentity.setId("email@gmail.com");
        assertThrows(WalletException.class,()->userIdentityOutputPort.deleteUserById(userIdentity.getId()));
    }

    @Test
    void deleteUser() {
        try {
            UserIdentity existingUser = userIdentityOutputPort.findByEmail(userIdentity.getEmail());
            assertEquals(userIdentity.getId(), existingUser.getId());
            userIdentityOutputPort.deleteUserByEmail(userIdentity.getEmail());
        }catch (WalletException exception){
            log.info("{} ->",exception.getMessage());
        }
        assertThrows(IdentityException.class,()-> userIdentityOutputPort.findByEmail(userIdentity.getEmail()));
    }

    @Test
    void findUserByNonExistingEmail() {
        userIdentity.setEmail("email@gmail.com");
        assertThrows(IdentityException.class, ()->userIdentityOutputPort.findByEmail(userIdentity.getEmail()));
    }

    @Test
    void findUserWithAnInvalidEmailFormat() {
        userIdentity.setEmail("email@email.com");
        assertThrows(WalletException.class, () -> userIdentityOutputPort.findByEmail(userIdentity.getEmail()));
    }

    @Test
    void  findUserWithNullEmail() {
        userIdentity.setEmail(null);
        assertThrows(WalletException.class, () -> userIdentityOutputPort.findByEmail(userIdentity.getEmail()));
    }

    @Test
    void  findUserWithEmptyEmail() {
        userIdentity.setEmail(StringUtils.EMPTY);
        assertThrows(WalletException.class, () -> userIdentityOutputPort.findByEmail(userIdentity.getEmail()));
    }

    @Test
    void findUserByEmail() throws WalletException {
            UserIdentity existingUser = userIdentityOutputPort.findByEmail(userIdentity.getId());
            assertEquals(existingUser.getId(),userIdentity.getId());
            UserIdentity userIdentity =  userIdentityOutputPort.findByEmail(userIdentity2.getEmail());
            assertNotNull(userIdentity);
    }
}
