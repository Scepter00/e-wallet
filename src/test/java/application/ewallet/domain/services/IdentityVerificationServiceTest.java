package application.ewallet.domain.services;

import application.ewallet.application.output.Identity.IdentityVerificationOutputPort;
import application.ewallet.domain.models.IdentityVerification;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
class IdentityVerificationServiceTest {

    @InjectMocks
    private IdentityVerificationService identityVerificationService;

    @Mock
    private IdentityVerificationOutputPort identityVerificationOutputPort;

    private IdentityVerification identityVerification;

    @BeforeEach
    void setUp() {
        identityVerification = new IdentityVerification();
        identityVerification.setNin("1234567890");
        identityVerification.setIdentityImage("image");
    }
}