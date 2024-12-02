package application.ewallet.infrastructure.adapters.output.persistence;

import application.ewallet.application.output.wallet.TransactionManagerOutputPort;
import application.ewallet.domain.exceptions.IdentityException;
import application.ewallet.domain.exceptions.WalletException;
import application.ewallet.domain.models.TransactionIdentity;
import application.ewallet.domain.validations.UserIdentityValidator;
import application.ewallet.infrastructure.adapters.output.persistence.entities.TransactionEntity;
import application.ewallet.infrastructure.adapters.output.persistence.mappers.TransactionMapper;
import application.ewallet.infrastructure.adapters.output.persistence.repositories.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionAdapter implements TransactionManagerOutputPort {
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    @Override
    public TransactionIdentity save(TransactionIdentity transactionIdentity) throws IdentityException {
        UserIdentityValidator.validateTransactionAmount(transactionIdentity.getAmount());
        UserIdentityValidator.validateDataElement(transactionIdentity.getWalletId());
        TransactionEntity transactionEntity = transactionMapper.mapToTransactionEntity(transactionIdentity);
        transactionEntity = transactionRepository.save(transactionEntity);
        return transactionMapper.mapToTransactionIdentity(transactionEntity);
    }
}
