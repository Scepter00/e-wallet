package application.ewallet.application.input;

import application.ewallet.domain.exceptions.WalletException;
import application.ewallet.domain.models.UserIdentity;
import application.ewallet.domain.models.WalletIdentity;

public interface CreateWalletUseCase {

    WalletIdentity createWalletIdentity(WalletIdentity walletIdentity) throws WalletException;

    WalletIdentity findWalletUserId(WalletIdentity walletIdentity) throws WalletException;
}
