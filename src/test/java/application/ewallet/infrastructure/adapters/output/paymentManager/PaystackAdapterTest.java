package application.ewallet.infrastructure.adapters.output.paymentManager;

import application.ewallet.application.output.payment.PaystackManagerOutputPort;
import application.ewallet.domain.exceptions.IdentityException;
import application.ewallet.infrastructure.adapters.input.data.request.PaystackRequest;
import application.ewallet.infrastructure.adapters.input.data.responses.PaystackResponse;
import application.ewallet.infrastructure.adapters.input.data.responses.PaystackVerificationResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;

@Slf4j
@SpringBootTest
class PayStackAdapterTest {

    @Autowired
    private PaystackManagerOutputPort paystackManagerOutputPort;

    @Mock
    private PaystackAdapter paystackAdapter;

    private PaystackRequest paystackRequest;

    @BeforeEach
    void setUp() {
        paystackRequest = PaystackRequest.builder().email("exceptionaloriented@gmail.com").
                amount(new BigDecimal("10000").multiply(new BigDecimal("100"))).build();
    }

    @Test
    void initializePayment() throws IdentityException {

        PaystackResponse paystackResponse = paystackManagerOutputPort.initialisePayment(paystackRequest);
        log.info("RESPONSE---> {}", paystackResponse);
        assertNotNull(paystackResponse);
        assertNotNull(paystackResponse.getData());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {
            StringUtils.SPACE,
            StringUtils.EMPTY,
            "email@gmail.com.com",
            "123456",
            "  email@gmail.com",
            "email@gmail.com  ",
            "  email@gmail.com  "
    })
    void initializePaymentEmailValidation(String email) throws IdentityException {
        paystackRequest.setEmail(email);
        doThrow(new IdentityException("Invalid email format"))
                .when(paystackAdapter).initialisePayment(paystackRequest);
        assertThrows(IdentityException.class, () ->
                paystackAdapter.initialisePayment(paystackRequest)
        );
    }

    @Test
    void verifyPayment() throws IdentityException {

        String reference = "9gw4jnjo01";
        PaystackVerificationResponse paystackVerificationResponse = paystackManagerOutputPort.verifyPayment(reference);
        assertNotNull(paystackVerificationResponse);
        log.info("OverAll {}", paystackVerificationResponse);
        assertNotNull(paystackVerificationResponse.getData());
        log.info("OverAll {}", paystackVerificationResponse.getData());
        assertNotNull(paystackVerificationResponse.getData().getAuthorization());
        log.info("OverAll {}", paystackVerificationResponse.getData().getAuthorization());
        assertNotNull(paystackVerificationResponse.getData().getCustomer());
        log.info("OverAll {}", paystackVerificationResponse.getData().getCustomer());
        assertNotNull(paystackVerificationResponse.getData().getLog());


    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {StringUtils.EMPTY, StringUtils.SPACE, "  reference  ", "reference   ", "  reference"})
    void verifyPaymentValidation(String refernce) throws IdentityException {
        doThrow(new IdentityException("Invalid email format"))
                .when(paystackAdapter).verifyPayment(refernce);

        assertThrows(IdentityException.class, () ->
                paystackAdapter.verifyPayment(refernce)
        );
    }
}