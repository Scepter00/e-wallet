package application.ewallet.infrastructure.adapters.output.paymentManager;

import application.ewallet.application.output.payment.PaystackManagerOutputPort;
import application.ewallet.domain.exceptions.IdentityException;
import application.ewallet.domain.validations.UserIdentityValidator;
import application.ewallet.infrastructure.adapters.config.PaystackConfiguration;
import application.ewallet.infrastructure.adapters.input.data.request.PaystackRequest;
import application.ewallet.infrastructure.adapters.input.data.responses.PaystackResponse;
import application.ewallet.infrastructure.adapters.input.data.responses.PaystackVerificationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaystackAdapter implements PaystackManagerOutputPort {

    private final PaystackConfiguration paystackConfiguration;
    private final RestTemplate restTemplate;


    @Override
    public PaystackResponse initialisePayment(PaystackRequest paystackRequest) throws IdentityException {
        UserIdentityValidator.validatePayStackInput(paystackRequest);
        String URL = paystackConfiguration.getPayInitialise_url();
        HttpHeaders httpHeaders = getHttpHeaders();
        HttpEntity<PaystackRequest> requestHttpEntity = new HttpEntity<>(paystackRequest, httpHeaders);
        ResponseEntity<PaystackResponse> responseEntity = restTemplate.exchange(
                URL,
                HttpMethod.POST,
                requestHttpEntity,
                PaystackResponse.class
        );
        return responseEntity.getBody();
    }


    @Override
    public PaystackVerificationResponse verifyPayment(String reference) throws IdentityException {
        UserIdentityValidator.validatePayStackReference(reference);
        HttpHeaders headers = getHttpHeaders();
        String verifyUrl = paystackConfiguration.getVerifyPayment_url() + reference;
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<PaystackVerificationResponse> response = restTemplate.exchange(
                verifyUrl,
                HttpMethod.GET,
                requestEntity,
                PaystackVerificationResponse.class
        );
        log.info("RequestEntity... {}", requestEntity);
        log.info("ResponseBody... {}", response.getBody());
        return response.getBody();
    }


    private HttpHeaders getHttpHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type", APPLICATION_JSON_VALUE);
        httpHeaders.set("Accept", APPLICATION_JSON_VALUE);
        httpHeaders.set("Authorization", "Bearer " + paystackConfiguration.getAuthorization_key());
        return httpHeaders;
    }
}