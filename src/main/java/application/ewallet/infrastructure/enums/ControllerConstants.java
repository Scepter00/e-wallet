package application.ewallet.infrastructure.enums;

import lombok.Getter;

@Getter
public enum ControllerConstants {
    RESPONSE_IS_SUCCESSFUL("Response is successful"),
    DELETE_SUCCESSFUL("Delete successful");
    private final String message;

    ControllerConstants(String message) {
        this.message = message;
    }
}
