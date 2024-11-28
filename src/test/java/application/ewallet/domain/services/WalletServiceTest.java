package application.ewallet.domain.services;

import application.ewallet.application.output.wallet.WalletManagerOutputPort;
import application.ewallet.domain.exceptions.WalletException;
import application.ewallet.domain.models.WalletIdentity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@Slf4j
@ExtendWith(MockitoExtension.class)
class WalletServiceTest {

    @Mock
    private WalletManagerOutputPort walletManagerOutputPort;

    @InjectMocks
    private WalletService walletService;

    private WalletIdentity walletIdentity;

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
    void createWallet_Success() throws WalletException {
        when(walletManagerOutputPort.getWalletByUserId(walletIdentity.getUserId())).thenReturn(Optional.empty());
        when(walletManagerOutputPort.save(any(WalletIdentity.class))).thenReturn(walletIdentity);

        WalletIdentity createdWallet = walletService.createWalletIdentity(walletIdentity);

        assertNotNull(createdWallet);
        assertEquals(walletIdentity.getUserId(), createdWallet.getUserId());
        verify(walletManagerOutputPort, times(1)).save(any(WalletIdentity.class));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {StringUtils.SPACE, StringUtils.EMPTY})
    void createWalletWhenUserIsNull(String userId) {
        walletIdentity.setUserId(userId);
        assertThrows(WalletException.class, () -> walletService.createWalletIdentity(walletIdentity));
    }
}