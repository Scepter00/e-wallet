package application.ewallet.infrastructure.adapters.output.identityVerificationManager;

import application.ewallet.application.output.Identity.IdentityVerificationOutputPort;
import application.ewallet.domain.models.IdentityVerification;
import application.ewallet.infrastructure.adapters.output.data.response.PremblyResponse;
import application.ewallet.infrastructure.enums.prembly.PremblyParameter;
import application.ewallet.infrastructure.exceptions.InfrastructureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import static application.ewallet.domain.enums.constants.WalletMessages.CREDENTIALS_SHOULD_NOT_BE_EMPTY;

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


    @Override
    public PremblyResponse verifyIdentity(IdentityVerification identityVerification) throws InfrastructureException {
        return getNinDetails(identityVerification);
    }

    public PremblyResponse getNinDetails(IdentityVerification verificationRequest) throws InfrastructureException {
        validateIdentityVerificationRequest(verificationRequest);
        ResponseEntity<PremblyResponse> responseEntity = getIdentityDetailsByNin(verificationRequest);
        log.info("Verification Result1: {}", responseEntity.getBody());
        return responseEntity.getBody();
    }

    private ResponseEntity<PremblyResponse> getIdentityDetailsByNin(IdentityVerification verificationRequest) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = getHttpHeaders();
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add(PremblyParameter.NIN_NUMBER.getValue(), verificationRequest.getNin());
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(formData, headers);
        String url = premblyUrl.concat(PremblyParameter.NIN_URL.getValue());
        log.info(url);
        ResponseEntity<PremblyResponse> responseEntity = ResponseEntity.ofNullable(PremblyResponse.builder().build());
        try {
            responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, PremblyResponse.class);
        } catch (HttpServerErrorException ex) {
            log.info("Prembly server error {}", ex.getMessage());
        }
        return responseEntity;
    }

    private HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(PremblyParameter.ACCEPT.getValue(), PremblyParameter.APPLICATION_JSON.getValue());
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add(PremblyParameter.APP_ID.getValue(), appId);
        headers.add(PremblyParameter.API_KEY.getValue(), apiKey);
        return headers;
    }

    private void validateIdentityVerificationRequest(IdentityVerification identityVerification) throws InfrastructureException {
        if (identityVerification == null || StringUtils.isEmpty(identityVerification.getNin()) || StringUtils.isEmpty(identityVerification.getIdentityImage()))
            throw new InfrastructureException(CREDENTIALS_SHOULD_NOT_BE_EMPTY.getMessage());
        if (identityVerification.getNin().length() != 11)
            throw new InfrastructureException("Invalid NIN");
    }
}
