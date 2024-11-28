package application.ewallet.domain.enums.constants;

import lombok.Getter;

@Getter
public enum IdentityMessages {
    USER_NOT_FOUND("User not found!"),
    INVALID_REGISTRATION_DETAILS("Invalid registration details"),
    INVALID_VALID_ROLE("Role not found"),
    ROLE_IS_REQUIRED("Role is required"),
    USER_IDENTITY_ALREADY_EXISTS("UserIdentity already exists"),
    INVALID_INPUT("Input cannot be empty"),
    INVALID_REFERENCE("Reference cannot be null or empty"),
    AMOUNT_CAN_NOT_BE_NULL("Amount cannot be null"),
    AMOUNT_CAN_NOT_EMPTY("Amount cannot be empty"),
    AMOUNT_CAN_NOT_BE_ZERO("Amount cannot be empty"),
    USER_PREVIOUSLY_VERIFIED("User has already created password before");

    private final String message;

    IdentityMessages(String message){
        this.message = message;
    }

}
