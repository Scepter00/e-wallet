package application.ewallet.domain.services;

import application.ewallet.application.input.CreateUserUseCase;
import application.ewallet.application.input.CreateWalletUseCase;
import application.ewallet.application.output.Identity.IdentityManagerOutputPort;
import application.ewallet.application.output.Identity.UserIdentityOutputPort;
import application.ewallet.domain.exceptions.WalletException;
import application.ewallet.domain.models.UserIdentity;
import application.ewallet.domain.models.WalletIdentity;
import application.ewallet.domain.validations.WalletValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static application.ewallet.domain.validations.UserIdentityValidator.validateUserIdentity;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserIdentityService implements CreateUserUseCase {
    private final UserIdentityOutputPort userIdentityOutputPort;
    private final IdentityManagerOutputPort identityManagerOutPutPort;
    private final CreateWalletUseCase createWalletUseCase;

    @Override
    public UserIdentity signup(UserIdentity userIdentity) throws WalletException {
        log.info("Signup UserIdentity =============> {}", userIdentity);
        validateUserIdentity(userIdentity);
        UserIdentity user = identityManagerOutPutPort.createUser(userIdentity);
        log.info("UserIdentity after been save to Keycloak ===============> {}", user);
        user.setCreatedBy(user.getId());
        user.setCreatedAt(LocalDateTime.now().toString());
        WalletIdentity walletIdentity = WalletIdentity.builder().build();
        walletIdentity.setUserId(user.getId());
        createWalletUseCase.createWalletIdentity(walletIdentity);
        user.setWalletIdentity(walletIdentity);
        return userIdentityOutputPort.save(user);
    }

    @Override
    public void deleteUser(UserIdentity userIdentity) throws WalletException {
        WalletValidator.validateUserId(userIdentity.getId());
        WalletValidator.validateDataElement(userIdentity.getEmail());
        UserIdentity foundUser = findUser(userIdentity);
        identityManagerOutPutPort.deleteUser(foundUser);
        userIdentityOutputPort.deleteUserById(foundUser.getId());
    }

    @Override
    public UserIdentity findUser(UserIdentity userIdentity) throws WalletException {
        identityManagerOutPutPort.findUser(userIdentity);
        userIdentityOutputPort.findById(userIdentity.getId());
        return userIdentity;
    }

    @Override
    public UserIdentity findUserById(String userId) throws WalletException {
        WalletValidator.validateDataElement(userId);
        return userIdentityOutputPort.findById(userId);
    }
}
