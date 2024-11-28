package application.ewallet.domain.services;

import application.ewallet.application.input.CreateTransactionUseCase;
import application.ewallet.application.output.Identity.UserIdentityOutputPort;
import application.ewallet.application.output.payment.PaystackManagerOutputPort;
import application.ewallet.application.output.wallet.WalletManagerOutputPort;
import application.ewallet.domain.exceptions.WalletException;
import application.ewallet.domain.models.UserIdentity;
import application.ewallet.domain.models.WalletIdentity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionService implements CreateTransactionUseCase {
    private final UserIdentityOutputPort userIdentityOutputPort;
    private PaystackManagerOutputPort payStackManagerOutputPort;
    private final WalletManagerOutputPort walletManagerOutputPort;

    @Override
    public void deposit(UserIdentity userIdentity, BigDecimal amount) throws WalletException {
        log.info("Depositing money into wallet for user: {}", userIdentity.getEmail());
        WalletIdentity wallet = walletManagerOutputPort.findByUserId(userIdentity.getId());
        if (wallet == null) {
            throw new WalletException("Wallet not found for user: " + userIdentity.getEmail());
        }
        wallet.setBalance(wallet.getBalance().add(amount));
        walletManagerOutputPort.save(wallet);
        log.info("Deposit successful. New balance: {}", wallet.getBalance());
    }
}
