package application.ewallet.domain.validations;

import application.ewallet.domain.enums.UserRole;
import application.ewallet.domain.enums.constants.WalletMessages;
import application.ewallet.domain.exceptions.IdentityException;
import application.ewallet.domain.exceptions.WalletException;
import application.ewallet.domain.models.IdentityVerification;
import application.ewallet.domain.models.UserIdentity;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;

public class WalletValidator {

    public static void validateUserEmail(String email) throws WalletException {
        if (StringUtils.isEmpty(email) || !EmailValidator.getInstance().isValid(email)) {
            throw new WalletException(WalletMessages.INVALID_EMAIL_ADDRESS.getMessage());
        }
    }

    public static void validateEmail(String email) throws WalletException {
        validateDataElement(email);
        validateUserEmail(email);
    }

    private static boolean isEmptyString(String dataElement) {
        return StringUtils.isEmpty(dataElement) || StringUtils.isBlank(dataElement);
    }

    public static void validateDataInput(UserIdentity userIdentity) throws WalletException {
        if(userIdentity == null){
            throw new WalletException(WalletMessages.INVALID_INPUT.getMessage());
        }
        validateDataElement(userIdentity.getEmail());
        validateDataElement(userIdentity.getFirstName());
        validateDataElement(userIdentity.getLastName());
        validateDataElement(userIdentity.getPassword());

    }

    public static void validateWalletId(String walletId) throws WalletException {
        if(isEmptyString(walletId)) {
            throw new WalletException(WalletMessages.WALLET_NOT_FOUND.getMessage());
        }
    }

    public static void validateUserId(String userId) throws WalletException {
        if(isEmptyString(userId)) {
            throw new WalletException(WalletMessages.INVALID_USER_ID.getMessage());
        }
    }

    public static void validateDataElement(String element) throws IdentityException {
        String trimmedDataElement = element != null ? element.trim() : StringUtils.EMPTY;
        if(isEmptyString(trimmedDataElement)){
            throw new IdentityException(WalletMessages.INVALID_INPUT.getMessage());
        }
    }

    public static void validateObjectInstance(Object instance) throws WalletException {
        if (ObjectUtils.isEmpty(instance)){
            throw new WalletException(WalletMessages.INVALID_OBJECT.getMessage());
        }
    }

    public static void validateIdentityVerificationRequest(IdentityVerification identityVerification) throws IdentityException {
        if(identityVerification == null){
            throw new IdentityException(WalletMessages.IDENTITY_SHOULD_NOT_BE_NULL.getMessage());
        }
        validateDataElement(identityVerification.getNin());
        validateDataElement(identityVerification.getIdentityImage());

    }
}
