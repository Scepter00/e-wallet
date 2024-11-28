package application.ewallet.application.output.wallet;

import application.ewallet.domain.exceptions.WalletException;
import application.ewallet.domain.models.WalletIdentity;

import java.util.Optional;

public interface WalletManagerOutputPort {

    WalletIdentity save (WalletIdentity walletIdentity) throws WalletException;

    WalletIdentity findByUserId(String userId) throws WalletException;

    Optional<WalletIdentity> getWalletByUserId(String userId) throws WalletException;

    void deleteWalletByUserId(String userId) throws WalletException;
}
