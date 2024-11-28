package application.ewallet.infrastructure.adapters.output.persistence;

import application.ewallet.application.output.wallet.WalletManagerOutputPort;
import application.ewallet.domain.models.WalletIdentity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class WalletAdapterTest {
    @Autowired
    private WalletManagerOutputPort walletManagerOutputPort;
    private WalletIdentity walletIdentity;

    @BeforeEach
    void setUp(){
        walletIdentity = new WalletIdentity();
        walletIdentity.setId("2c521790-563a-4449-a4bd-459bd5a2d4d7");
        walletIdentity.setUserId("2c521790-563a-4449-a4bd-459bd5a2d4d7");
        walletIdentity.setBalance(BigDecimal.valueOf(10000));
        walletIdentity.setCreatedBy("2c521790-563a-4449-a4bd-459bd5a2d4d7");
        walletIdentity.setCreatedAt(LocalDateTime.now().toString());
    }

    @Test
    void saveWallet(){
        try{
            WalletIdentity savedWallet = walletManagerOutputPort.save(walletIdentity);
            assertNotNull(savedWallet);
            WalletIdentity findWallet = walletManagerOutputPort.findByUserId(walletIdentity.getUserId());
            assertEquals(findWallet.getId(),savedWallet.getId());
            assertEquals(findWallet.getUserId(),savedWallet.getUserId());
            assertEquals(findWallet.getCreatedBy(),savedWallet.getCreatedBy());
        }catch (Exception exception){
            log.error("{} {}->",exception.getClass().getName(), exception.getMessage());
        }
    }

    @Test
    void saveWalletWithNullUserId() {
        walletIdentity.setUserId(null);
        assertThrows(Exception.class, () -> walletManagerOutputPort.save(walletIdentity));
    }

    @Test
    void saveWalletWithNullWalletId() {
        walletIdentity.setId(null);
        assertThrows(Exception.class, () -> walletManagerOutputPort.save(walletIdentity));
    }

    @Test
    void saveWalletWithNullBalance() {
        walletIdentity.setBalance(null);
        assertThrows(Exception.class, () -> walletManagerOutputPort.save(walletIdentity));
    }
}