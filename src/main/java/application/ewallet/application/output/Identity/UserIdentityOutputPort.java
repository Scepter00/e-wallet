package application.ewallet.application.output.Identity;

import application.ewallet.domain.exceptions.WalletException;
import application.ewallet.domain.models.UserIdentity;

public interface UserIdentityOutputPort {
    UserIdentity save(UserIdentity userIdentity) throws WalletException;
    UserIdentity findByEmail(String email) throws WalletException;
    void deleteUserByEmail(String email) throws WalletException;
    UserIdentity findById(String id) throws WalletException;
    void deleteUserById(String id) throws WalletException;
}
