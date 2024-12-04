package application.ewallet.domain.services;

import application.ewallet.application.input.IdentityVerificationUseCase;
import application.ewallet.domain.models.IdentityVerification;
import application.ewallet.infrastructure.adapters.input.data.responses.premblyResponse.PremblyResponse;
import org.springframework.stereotype.Service;

@Service
public class IdentityVerificationService implements IdentityVerificationUseCase {

    @Override
    public PremblyResponse verifyIdentity(IdentityVerification identityVerification) {
        return null;
    }
}
