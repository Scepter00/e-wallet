package application.ewallet.application.output.Identity;

import application.ewallet.domain.models.IdentityVerification;
import application.ewallet.infrastructure.adapters.output.data.response.PremblyResponse;
import application.ewallet.infrastructure.exceptions.InfrastructureException;

public interface IdentityVerificationOutputPort {

    PremblyResponse verifyIdentity (IdentityVerification identityVerification) throws InfrastructureException;

    PremblyResponse getNinDetails(IdentityVerification verificationRequest) throws InfrastructureException;
}
