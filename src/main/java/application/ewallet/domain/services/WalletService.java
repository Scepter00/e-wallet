package application.ewallet.domain.services;

import application.ewallet.application.input.CreateWalletUseCase;
import application.ewallet.application.output.wallet.WalletManagerOutputPort;
import application.ewallet.domain.enums.WalletStatus;
import application.ewallet.domain.enums.constants.WalletMessages;
import application.ewallet.domain.exceptions.WalletException;
import application.ewallet.domain.models.WalletIdentity;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class WalletService implements CreateWalletUseCase {

    private final WalletManagerOutputPort walletManagerOutputPort;

    @Override
    public WalletIdentity createWalletIdentity(WalletIdentity walletIdentity) throws WalletException {
        createWallet(walletIdentity);
        return walletManagerOutputPort.save(walletIdentity);
    }

    @Override
    public WalletIdentity findWalletUserId(WalletIdentity walletIdentity) throws WalletException {
        return walletManagerOutputPort.findByUserId(walletIdentity.getUserId());
    }

    private WalletIdentity createWallet(WalletIdentity walletIdentity) throws WalletException {
        Optional<WalletIdentity> foundWallet = walletManagerOutputPort.getWalletByUserId(walletIdentity.getUserId());
        if (foundWallet.isPresent() && WalletStatus.ACTIVE.equals(foundWallet.get().getWalletStatus())) {
            throw new WalletException(WalletMessages.WALLET_ALREADY_EXISTS.getMessage());
        }
        return WalletIdentity.builder()
                .userId(walletIdentity.getUserId())
                .balance(BigDecimal.ZERO)
                .transactionIdentity(new ArrayList<>())
                .createdAt(LocalDateTime.now().toString())
                .createdBy(walletIdentity.getCreatedBy())
                .build();
    }
}
