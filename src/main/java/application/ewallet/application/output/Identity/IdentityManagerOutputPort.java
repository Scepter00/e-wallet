package application.ewallet.application.output.Identity;

import application.ewallet.domain.exceptions.WalletException;
import application.ewallet.domain.models.UserIdentity;

public interface IdentityManagerOutputPort {

    UserIdentity createUser(UserIdentity userIdentity) throws WalletException;

    UserIdentity login(UserIdentity userIdentity) throws WalletException;

    UserIdentity findUser(UserIdentity userIdentity) throws WalletException;

    void deleteUser(UserIdentity userIdentity) throws WalletException;
}
