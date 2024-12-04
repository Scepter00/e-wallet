package application.ewallet.infrastructure.adapters.output.persistence;

import application.ewallet.application.output.wallet.TransactionManagerOutputPort;
import application.ewallet.domain.enums.TransactionType;
import application.ewallet.domain.exceptions.IdentityException;
import application.ewallet.domain.models.TransactionIdentity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class TransactionAdapterTest {
    @Autowired
    private TransactionManagerOutputPort transactionManagerOutputPort;
    private TransactionIdentity transactionIdentity;

    @BeforeEach
    void setUp() {
        transactionIdentity =
                TransactionIdentity.builder().amount(new BigDecimal("100"))
                        .transactionType(TransactionType.DEPOSIT)
                        .payStackReference("rygertdccvv").description("school fees")
                        .walletId("wallet").build();
    }

    @Test
    void saveTransaction() throws IdentityException {
            TransactionIdentity transactionIdentity1 = transactionManagerOutputPort.save(transactionIdentity);
            assertNotNull(transactionIdentity);
            log.info("Transaction ----> {}", transactionIdentity);
            assertEquals(transactionIdentity1.getAmount(), transactionIdentity.getAmount());
    }

    @Test
    void testAmountCannotBeZero() {
        transactionIdentity.setAmount(BigDecimal.ZERO);
        assertThrows(IdentityException.class, () -> transactionManagerOutputPort.save(transactionIdentity));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"-1.0", "-0.01"})
    void testAmountCannotBeNegative(String amount) {
        transactionIdentity.setAmount(new BigDecimal(amount));
        assertThrows(IdentityException.class, () -> transactionManagerOutputPort.save(transactionIdentity));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {StringUtils.EMPTY,StringUtils.SPACE})
    void validateUserId(String walletId) {
        transactionIdentity.setWalletId(walletId);
        assertThrows(IdentityException.class, () -> transactionManagerOutputPort.save(transactionIdentity));
    }
}