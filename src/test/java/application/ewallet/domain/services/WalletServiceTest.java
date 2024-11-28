package application.ewallet.domain.services;

import application.ewallet.application.input.CreateWalletUseCase;
import application.ewallet.application.output.wallet.WalletManagerOutputPort;
import application.ewallet.domain.enums.WalletStatus;
import application.ewallet.domain.exceptions.WalletException;
import application.ewallet.domain.models.UserIdentity;
import application.ewallet.domain.models.WalletIdentity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class WalletServiceTest {

    @Autowired
    private CreateWalletUseCase createWalletUseCase;
    private WalletIdentity walletIdentity;
    private UserIdentity userIdentity;

    @BeforeEach
    void setUp() {
        walletIdentity = WalletIdentity.builder()
                .userId("2c521790-563a-4449-a4bd-459bd5a2d4d7")
                .balance(BigDecimal.ZERO)
                .createdAt(LocalDateTime.now().toString())
                .transactionIdentity(new ArrayList<>())
                .build();
    }

    @Test
    void createWallet() throws WalletException {
        WalletIdentity wallet = createWalletUseCase.createWalletIdentity(walletIdentity);
        log.info("Wallet Identity -----> {}", wallet);
        assertNotNull(wallet);
        assertNotNull(wallet.getUserId());
        assertEquals(wallet.getUserId(),walletIdentity.getUserId());
    }
}