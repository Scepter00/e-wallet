package application.ewallet.domain.validations;

import application.ewallet.domain.enums.constants.IdentityMessages;
import application.ewallet.domain.exceptions.IdentityException;
import application.ewallet.domain.exceptions.WalletException;
import application.ewallet.domain.models.UserIdentity;
import application.ewallet.infrastructure.adapters.input.data.request.PaystackRequest;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

import static application.ewallet.domain.enums.constants.IdentityMessages.INVALID_VALID_ROLE;

public class UserIdentityValidator extends WalletValidator {
    public static void validateUserIdentity(UserIdentity userIdentity) throws WalletException {
        WalletValidator.validateObjectInstance(userIdentity);
        if (ObjectUtils.isEmpty(userIdentity.getUserRole())|| StringUtils.isEmpty(userIdentity.getUserRole().name()))
            throw new IdentityException(INVALID_VALID_ROLE.getMessage());

        WalletValidator.validateEmail(userIdentity.getEmail());
        WalletValidator.validateDataElement(userIdentity.getFirstName());
        WalletValidator.validateDataElement(userIdentity.getLastName());
    }

    public static void validateTransactionAmount(BigDecimal amount) throws IdentityException {
        if (amount == null) {
            throw new IdentityException(IdentityMessages.AMOUNT_CAN_NOT_BE_NULL.getMessage());
        }

        String amountString = amount.toString().trim();

        if (amountString.isEmpty()) {
            throw new IdentityException(IdentityMessages.AMOUNT_CAN_NOT_EMPTY.getMessage());
        }

        BigDecimal trimmedAmount = new BigDecimal(amountString);

        if (trimmedAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IdentityException(IdentityMessages.AMOUNT_CAN_NOT_BE_ZERO.getMessage());
        }
        if (trimmedAmount.compareTo(BigDecimal.ZERO) == 0) {
            throw new IdentityException(IdentityMessages.AMOUNT_CAN_NOT_BE_ZERO.getMessage());
        }
    }



    public static void validatePayStackInput(PaystackRequest payStackRequest) throws IdentityException {
        if (payStackRequest == null) {
            throw new IdentityException(IdentityMessages.INVALID_INPUT.getMessage());
        }
    }

    public static void validatePayStackReference(String reference) throws IdentityException {
        if (StringUtils.isEmpty(reference) ||StringUtils.isBlank(reference)) {
            throw new IdentityException(IdentityMessages.INVALID_REFERENCE.getMessage());
        }
    }
}
