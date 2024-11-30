package application.ewallet.application.input;

import application.ewallet.domain.exceptions.WalletException;
import application.ewallet.domain.models.UserIdentity;

public interface CreateUserUseCase {

    UserIdentity signup(UserIdentity userIdentity) throws WalletException;


    UserIdentity findUser(UserIdentity userIdentity) throws WalletException;

    UserIdentity findUserById(String userId) throws WalletException;

    void deleteUser(UserIdentity userIdentity) throws WalletException;
}
