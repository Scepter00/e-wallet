package application.ewallet.application.input;

import application.ewallet.domain.models.IdentityVerification;
import application.ewallet.infrastructure.adapters.input.data.responses.premblyResponse.PremblyResponse;

public interface IdentityVerificationUseCase {

    PremblyResponse verifyIdentity(IdentityVerification identityVerification);
}
