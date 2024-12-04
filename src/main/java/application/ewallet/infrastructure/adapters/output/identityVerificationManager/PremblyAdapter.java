package application.ewallet.infrastructure.adapters.output.identityVerificationManager;

import application.ewallet.application.output.Identity.IdentityVerificationOutputPort;
import application.ewallet.domain.enums.constants.WalletMessages;
import application.ewallet.domain.exceptions.IdentityException;
import application.ewallet.domain.models.IdentityVerification;
import application.ewallet.domain.validations.WalletValidator;
import application.ewallet.infrastructure.adapters.input.data.responses.premblyResponse.PremblyLivelinessResponse;
import application.ewallet.infrastructure.adapters.input.data.responses.premblyResponse.PremblyNinResponse;
import application.ewallet.infrastructure.adapters.input.data.responses.premblyResponse.PremblyResponse;
import application.ewallet.infrastructure.enums.prembly.PremblyParameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class PremblyAdapter implements IdentityVerificationOutputPort {

    @Value("${PREMBLY_URL}")
    private String premblyUrl;

    @Value("${PREMBLY_APP_ID}")
    private String appId;

    @Value("${PREMBLY_APP_KEY}")
    private String apiKey;

    private final RestTemplate restTemplate;



    @Override
    public PremblyResponse verifyIdentity(IdentityVerification identityVerification) throws IdentityException {
        WalletValidator.validateIdentityVerificationRequest(identityVerification);
        if (identityVerification.getNin() != null) {
            return (identityVerification.getIdentityImage() != null)
                    ? verifyNinLikeness(identityVerification)
                    : verifyNin(identityVerification);
        }
        throw new IdentityException("Either NIN or BVN must be provided.");
    }


    @Override
    public PremblyResponse verifyNin(IdentityVerification identityVerification) throws IdentityException {
        validateInput(identityVerification);
        String url = premblyUrl.concat(PremblyParameter.NIN_URL.getValue());
        HttpHeaders httpHeaders = getHttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put(PremblyParameter.NIN.getValue(), identityVerification.getNin());
        HttpEntity<Map<String, String>> requestHttpEntity = new HttpEntity<>(requestBody, httpHeaders);
        ResponseEntity<PremblyNinResponse> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestHttpEntity,
                PremblyNinResponse.class
        );

        return responseEntity.getBody();
    }

    @Override
    public PremblyResponse verifyNinLikeness(IdentityVerification identityVerification) throws IdentityException {
        return getNinDetails(identityVerification);
    }

    @Override
    public PremblyResponse verifyLiveliness(IdentityVerification identityVerification) {
        String url = premblyUrl.concat(PremblyParameter.NIN_LIVENESS_URL.getValue());
        HttpHeaders httpHeaders = getHttpHeaders();
        HttpEntity<IdentityVerification> requestHttpEntity = new HttpEntity<>(identityVerification, httpHeaders);
        ResponseEntity<PremblyLivelinessResponse> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestHttpEntity,
                PremblyLivelinessResponse.class
        );
        return responseEntity.getBody();
    }

    public PremblyResponse getNinDetails(IdentityVerification identityVerification) throws IdentityException {
        PremblyResponse premblyResponse = getIdentityDetailsByNin(identityVerification);
        premblyResponse.getVerification().updateValidIdentity();
        log.info("Response: {}", premblyResponse);
        return premblyResponse;
    }

    private PremblyResponse getIdentityDetailsByNin(IdentityVerification identityVerification) {
        HttpEntity<MultiValueMap<String, String>> entity = createRequestEntity(identityVerification);
        String url = premblyUrl.concat(PremblyParameter.NIN_FACE_URL.getValue());
        log.info(url);
        ResponseEntity<PremblyNinResponse> responseEntity = ResponseEntity.ofNullable(PremblyNinResponse.builder().build());
        log.info("Response {}",responseEntity.getBody());
        try {
            responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, PremblyNinResponse.class);
        } catch (HttpServerErrorException ex) {
            log.info("Prembly server error {}", ex.getMessage());
            log.error("Prembly Server error {}", ex.getMessage());
        }
        return responseEntity.getBody();
    }

    private HttpEntity<MultiValueMap<String, String>> createRequestEntity(IdentityVerification verificationRequest) {
        HttpHeaders headers = getHttpHeaders();
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add(PremblyParameter.NUMBER.getValue(), verificationRequest.getNin());
        formData.add(PremblyParameter.IMAGE.getValue(), verificationRequest.getIdentityImage());
        log.debug("Prepared form data: {}", formData);
        return new HttpEntity<>(formData, headers);
    }

    private HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(PremblyParameter.ACCEPT.getValue(), PremblyParameter.APPLICATION_JSON.getValue());
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add(PremblyParameter.APP_ID.getValue(), appId);
        headers.add(PremblyParameter.API_KEY.getValue(), apiKey);
        return headers;
    }

    public void validateInput(IdentityVerification identityVerification) throws IdentityException {
        validateIdentityVerification(identityVerification);
        if (identityVerification.getIdentityImage() != null) {
            throw new IdentityException(WalletMessages.ONLY_IDENTITY_NUMBER.getMessage());
        }
    }

        public void validateIdentityVerification(IdentityVerification identityVerification) throws IdentityException {
            if(identityVerification == null){
                throw new IdentityException(WalletMessages.IDENTITY_SHOULD_NOT_BE_NULL.getMessage());
            }
        }
}
