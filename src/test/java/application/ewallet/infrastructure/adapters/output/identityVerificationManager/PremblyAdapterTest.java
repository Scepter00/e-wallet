package application.ewallet.infrastructure.adapters.output.identityVerificationManager;

import application.ewallet.infrastructure.enums.prembly.PremblyResponseCode;
import application.ewallet.infrastructure.enums.prembly.PremblyVerificationMessages;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import application.ewallet.application.output.Identity.IdentityVerificationOutputPort;
import application.ewallet.domain.models.IdentityVerification;
import application.ewallet.infrastructure.adapters.output.data.response.PremblyResponse;
import application.ewallet.infrastructure.exceptions.InfrastructureException;
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

    @BeforeEach
    void setUp(){
        identityVerification = IdentityVerification.builder().nin("12345678901")
                .identityImage("https://res.cloudinary.com/dh3i1wodq/image/upload/v1675417496/cbimage_3_drqdoc.jpg").build();
    }

    @Test
    void verifyIdentityWithValidNinAndValidImage() throws InfrastructureException {
        PremblyResponse response = identityVerificationOutputPort.verifyIdentity(identityVerification);
        log.info("Response ----> {}", response);
        assertNotNull(response);
        assertEquals(PremblyResponseCode.SUCCESSFUL.getCode(),response.getResponseCode());
        assertTrue(response.isVerificationCallSuccessful());
        assertEquals(PremblyResponseCode.SUCCESSFUL.getCode(),response.getFaceData().getResponseCode());
        assertTrue(response.getFaceData().isFaceVerified());
        assertTrue(response.getVerification().isValidNin());
        assertEquals(response.getNinData().getNin(),identityVerification.getNin());
    }

    @Test
    void verifyIdentityWhenImageDoesNotMatch() throws InfrastructureException {
        identityVerification.setIdentityImage("https://res.cloudinary.com/drhrd1xkn/image/upload/v1732042468/gi2ppo8hsivajcn74idz.jpg");
        PremblyResponse response = identityVerificationOutputPort.verifyIdentity(identityVerification);
        assertNotNull(response);
        assertEquals(PremblyResponseCode.SUCCESSFUL_RECORD_NOT_FOUND.getCode(),response.getFaceData().getResponseCode());
        assertTrue(response.isVerificationCallSuccessful());
        assertEquals(PremblyVerificationMessages.VERIFIED.getMessage(),response.getVerification().getStatus());
        assertFalse(response.getFaceData().isFaceVerified());
        assertTrue(response.getVerification().isValidNin());

    }

    @Test
    void verifyIdentityWithInvalidNin() throws InfrastructureException {
        identityVerification.setNin("12345678901");
        PremblyResponse response = identityVerificationOutputPort.verifyIdentity(identityVerification);
        log.info("......{}", response);
        assertNotNull(response);
        assertTrue(response.isVerificationCallSuccessful());
        assertEquals(PremblyResponseCode.SUCCESSFUL_RECORD_NOT_FOUND.getCode(),response.getResponseCode());
    }

    @Test
    void verifyIdentityWhenImageIsNotPosition() throws InfrastructureException {
        identityVerification.setIdentityImage("https://res.cloudinary.com/drhrd1xkn/image/upload/v1732027712/ez15xfsdj3whhd5kwscs.jpg");
        PremblyResponse response = identityVerificationOutputPort.verifyIdentity(identityVerification);
        assertNotNull(response);
        assertTrue(response.isVerificationCallSuccessful());
        assertFalse(response.getFaceData().isFaceVerified());
        assertEquals(PremblyVerificationMessages.PREMBLY_FACE_CONFIRMATION.getMessage(),response.getFaceData().getMessage());
    }

    @Test
    void verifyIdentityWhenBalanceIsInsufficient() throws InfrastructureException {
        PremblyResponse insufficientBalanceResponse = PremblyResponse.builder()
                .responseCode(PremblyResponseCode.INSUFFICIENT_WALLET_BALANCE.getCode())
                .verificationCallSuccessful(false)
                .build();

        when(premblyAdapter.verifyIdentity(identityVerification))
                .thenReturn(insufficientBalanceResponse);

        PremblyResponse response =premblyAdapter.verifyIdentity(identityVerification);
        assertNotNull(response);
        assertEquals(PremblyResponseCode.INSUFFICIENT_WALLET_BALANCE.getCode(), response.getResponseCode());
        assertFalse(response.isVerificationCallSuccessful());

        verify(premblyAdapter, times(1)).verifyIdentity(identityVerification);
    }

    @Test
    void verifyIdentityWithNullIdentityVerification() {
        assertThrows(InfrastructureException.class, () -> identityVerificationOutputPort.verifyIdentity(null));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {StringUtils.EMPTY, StringUtils.SPACE})
    void verifyIdentityWithEmptyIdentityId(String nin) {
        identityVerification.setNin(nin);
        assertThrows(InfrastructureException.class, () -> identityVerificationOutputPort.verifyIdentity(identityVerification));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {StringUtils.EMPTY, StringUtils.SPACE})
    void verifyIdentityWithNullIdentityImage(String url) {
        identityVerification.setIdentityImage(url);
        assertThrows(InfrastructureException.class, () -> identityVerificationOutputPort.verifyIdentity(identityVerification));
    }
}