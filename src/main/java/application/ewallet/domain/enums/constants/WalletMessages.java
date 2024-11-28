package application.ewallet.domain.enums.constants;

import lombok.Getter;

@Getter
public enum WalletMessages {
    INVALID_EMAIL_ADDRESS("Invalid email address"),
    INVALID_OBJECT("Object not found"),
    EMAIL_NOT_FOUND("Email address cannot be found"),
    INVALID_INPUT("Invalid input"),
    INVALID_USER_ID("User id not found"),
    WALLET_NOT_FOUND("Wallet not found"),
    CREDENTIALS_SHOULD_NOT_BE_EMPTY("Credentials should not be empty"),
    WALLET_ALREADY_EXISTS("Wallet already exists"),
    INVALID_EMAIL_OR_PASSWORD("Invalid email or password"),
    INVALID_EMAIL("Invalid email"),
    INVALID_PASSWORD_OR_PASSWORD("Invalid email or password"),
    EMAIL_HAS_BEEN_USED("Email has been used"),
    WALLET_ALREADY_EXIST("Already exist");

    private final String message;

    WalletMessages(String message) {
        this.message = message;
    }
}
