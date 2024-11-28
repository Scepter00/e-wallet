package application.ewallet.application.output.wallet;

import application.ewallet.domain.exceptions.IdentityException;
import application.ewallet.domain.models.TransactionIdentity;

public interface TransactionManagerOutputPort {
    TransactionIdentity save(TransactionIdentity transactionIdentity) throws IdentityException;
}
