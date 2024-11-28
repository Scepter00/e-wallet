package application.ewallet.domain.exceptions;

public class IdentityException extends WalletException {

    public IdentityException(String message){
        super(message);
    }

    public IdentityException(String message, Throwable cause) {
        super(message, cause);
    }

    public IdentityException(Throwable cause) {
        super(cause);
    }


}
