package application.ewallet.infrastructure.adapters.output.identityVerificationManager;

import application.ewallet.domain.enums.constants.WalletMessages;
import application.ewallet.domain.exceptions.IdentityException;
import application.ewallet.infrastructure.adapters.input.data.responses.premblyResponse.PremblyLivelinessResponse;
import application.ewallet.infrastructure.adapters.input.data.responses.premblyResponse.PremblyNinResponse;
import application.ewallet.infrastructure.enums.prembly.PremblyResponseCode;
import application.ewallet.infrastructure.enums.prembly.PremblyVerificationMessages;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import application.ewallet.application.output.Identity.IdentityVerificationOutputPort;
import application.ewallet.domain.models.IdentityVerification;
import lombok.extern.slf4j.Slf4j;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@Slf4j
@SpringBootTest
class PremblyAdapterTest {

    @Autowired
    private IdentityVerificationOutputPort identityVerificationOutputPort;

    @Mock
    PremblyAdapter premblyAdapter;

    private IdentityVerification identityVerification;
    private IdentityVerification ninIdentityVerification;
    private IdentityVerification livelinessVerification;


    @BeforeEach
    void setUp() {
        identityVerification = IdentityVerification.builder().nin("12345678903")
                .identityImage("https://res.cloudinary.com/drhrd1xkn/image/upload/v1732027769/.jpg").build();

        livelinessVerification = IdentityVerification.builder()
                .identityImage("https://res.cloudinary.com/drhrd1xkn/image/upload/v1732027769/.jpg").build();

        ninIdentityVerification = IdentityVerification.builder().nin("1234567890").build();
    }

    @Test
    void verifyIdentityWithValidNinAndValidImage() throws IdentityException {
        PremblyNinResponse response = (PremblyNinResponse) identityVerificationOutputPort.verifyNinLikeness(identityVerification);
        log.info("Response {}",response);
        assertNotNull(response);
        assertEquals(PremblyResponseCode.SUCCESSFUL.getCode(), response.getResponseCode());
        assertTrue(response.isVerificationCallSuccessful());
        assertEquals(PremblyResponseCode.SUCCESSFUL.getCode(), response.getFaceData().getResponseCode());
        assertTrue(response.getFaceData().isFaceVerified());
        assertTrue(response.getVerification().isValidIdentity());
        assertEquals(identityVerification.getNin(), response.getNinData().getNin());
    }

    @Test
    void verifyIdentityWithValidAndImageDoesNotMatch() throws IdentityException {
        identityVerification.setIdentityImage("https://res.cloudinary.com/drhrd1xkn/image/upload/v1732042468/gi2ppo8hsivajcn74idz.jpg");
        PremblyNinResponse response = (PremblyNinResponse) identityVerificationOutputPort.verifyIdentity(identityVerification);
        assertNotNull(response);
        assertEquals(PremblyResponseCode.SUCCESSFUL_RECORD_NOT_FOUND.getCode(), response.getFaceData().getResponseCode());
        assertTrue(response.isVerificationCallSuccessful());
        assertEquals(PremblyVerificationMessages.VERIFIED.getMessage(), response.getVerification().getStatus());
        assertFalse(response.getFaceData().isFaceVerified());
        assertTrue(response.getVerification().isValidIdentity());
    }

    @Test
    void verifyIdentityWithInvalidNin() throws IdentityException {
        identityVerification.setNin("12345678901");
        PremblyNinResponse response = (PremblyNinResponse) identityVerificationOutputPort.verifyNinLikeness(identityVerification);
        log.info("......{}", response);
        assertNotNull(response);
        assertTrue(response.isVerificationCallSuccessful());
        assertEquals(PremblyResponseCode.SUCCESSFUL_RECORD_NOT_FOUND.getCode(), response.getResponseCode());
    }

    @Test
    void verifyIdentityWhenImageIsNotPosition() throws IdentityException {
        identityVerification.setIdentityImage("https://res.cloudinary.com/drhrd1xkn/image/upload/v1732027712/ez15xfsdj3whhd5kwscs.jpg");
        PremblyNinResponse response = (PremblyNinResponse) identityVerificationOutputPort.verifyNinLikeness(identityVerification);
        assertNotNull(response);
        assertTrue(response.isVerificationCallSuccessful());
        assertFalse(response.getFaceData().isFaceVerified());
        assertEquals(PremblyVerificationMessages.PREMBLY_FACE_CONFIRMATION.getMessage(), response.getFaceData().getMessage());
    }

    @Test
    void verifyIdentityWhenBalanceIsInsufficient() throws IdentityException {
        PremblyNinResponse insufficientBalanceResponse = PremblyNinResponse.builder()
                .responseCode(PremblyResponseCode.INSUFFICIENT_WALLET_BALANCE.getCode())
                .verificationCallSuccessful(false)
                .build();

        when(premblyAdapter.verifyNinLikeness(identityVerification))
                .thenReturn(insufficientBalanceResponse);

        PremblyNinResponse response = (PremblyNinResponse) premblyAdapter.verifyNinLikeness(identityVerification);
        assertNotNull(response);
        assertEquals(PremblyResponseCode.INSUFFICIENT_WALLET_BALANCE.getCode(), response.getResponseCode());
        assertFalse(response.isVerificationCallSuccessful());

        verify(premblyAdapter, times(1)).verifyIdentity(identityVerification);
    }

    @Test
    void verifyLivelinessTest(){
        PremblyLivelinessResponse livelinessResponse = (PremblyLivelinessResponse) identityVerificationOutputPort.verifyLiveliness(livelinessVerification);
        assertNotNull(livelinessResponse);
        log.info("Response...{}",livelinessResponse);
        assertTrue(livelinessResponse.isStatus());
        assertEquals(PremblyResponseCode.SUCCESSFUL.getCode(),livelinessResponse.getResponseCode());
        assertTrue(livelinessResponse.getVerification().isValidIdentity());
    }

    @Test
    void verifyIdentityWithNullIdentityVerification() {
        IdentityException exception = assertThrows(
                IdentityException.class,
                () -> identityVerificationOutputPort.verifyNinLikeness(null)
        );
        assertEquals(WalletMessages.IDENTITY_SHOULD_NOT_BE_NULL.getMessage(), exception.getMessage());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {StringUtils.EMPTY, StringUtils.SPACE})
    void verifyIdentityWithEmptyIdentityId(String nin) {
        identityVerification.setNin(nin);
        IdentityException exception =
                assertThrows(IdentityException.class, () -> identityVerificationOutputPort.verifyNinLikeness(identityVerification));
        assertEquals(WalletMessages.EMPTY_INPUT_FIELD_ERROR.getMessage(),exception.getMessage());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {StringUtils.EMPTY, StringUtils.SPACE})
    void verifyIdentityWithNullIdentityImage(String url) {
        identityVerification.setIdentityImage(url);
        IdentityException exception =
                assertThrows(IdentityException.class, () -> identityVerificationOutputPort.verifyNinLikeness(identityVerification));
        assertEquals(WalletMessages.EMPTY_INPUT_FIELD_ERROR.getMessage(),exception.getMessage());
    }

    @Test
    void verifyNinIdentity() throws IdentityException {
        PremblyNinResponse response = (PremblyNinResponse) identityVerificationOutputPort.verifyIdentity(ninIdentityVerification);
        assertNotNull(response);
        assertTrue(response.isVerificationCallSuccessful());
        assertEquals(PremblyResponseCode.SUCCESSFUL.getCode(),response.getResponseCode());
        assertEquals(response.getNinData().getNin(),ninIdentityVerification.getNin());
    }
}