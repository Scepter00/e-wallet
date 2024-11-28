package application.ewallet.application.input;

import application.ewallet.domain.exceptions.WalletException;
import application.ewallet.domain.models.UserIdentity;

import java.math.BigDecimal;

public interface CreateTransactionUseCase {
    void deposit(UserIdentity userIdentity, BigDecimal amount) throws WalletException;
}
