package application.ewallet.infrastructure.enums.prembly;

import lombok.Getter;

@Getter
public enum PremblyVerificationMessages {
    SERVICE_UNAVAILABLE("This Service is Unavailable. Please try again in a few minutes."),
    NIN_NOT_FOUND("This NIN cannot be found. Please provide a correct NIN."),
    VERIFICATION_SUCCESSFUL("Verification Successful"),
    NIN_VERIFIED("Verified"),
    VERIFICATION_UNSUCCESSFUL("Verification Unsuccessful"),
    PREMBLY_UNAVAILABLE("Prembly server error."),
    INSUFFICIENT_WALLET_BALANCE("Insufficient wallet balance"),
    PREMBLY_FACE_CONFIRMATION("Liveliness check failed: Face Occluded.... kindly try better positioning"),
    PREMBY_FACE_DOES_NOT_MATCH("Face does not match"),
    PENDING("PENDING"),
    VERIFIED("VERIFIED"),
    NOT_VERIFIED("NOT_VERIFIED");

    private final String message;

    PremblyVerificationMessages(String message) {
        this.message = message;
    }
}
