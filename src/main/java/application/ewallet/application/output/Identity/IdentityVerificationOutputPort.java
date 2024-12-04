package application.ewallet.application.output.Identity;

import application.ewallet.domain.exceptions.IdentityException;
import application.ewallet.domain.models.IdentityVerification;
import application.ewallet.infrastructure.adapters.input.data.responses.premblyResponse.PremblyResponse;

public interface IdentityVerificationOutputPort {

    PremblyResponse verifyIdentity (IdentityVerification identityVerification) throws IdentityException;

    PremblyResponse verifyNin(IdentityVerification identityVerification) throws IdentityException;

    PremblyResponse verifyNinLikeness(IdentityVerification identityVerification) throws IdentityException;

    PremblyResponse verifyLiveliness(IdentityVerification identityVerification);
}
