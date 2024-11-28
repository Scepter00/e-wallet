package application.ewallet.infrastructure.adapters.output.persistence.mappers;

import application.ewallet.domain.models.TransactionIdentity;
import application.ewallet.infrastructure.adapters.output.persistence.entities.TransactionEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel= "spring")
public interface TransactionMapper {
    TransactionEntity mapToTransactionEntity(TransactionIdentity transactionIdentity);

    TransactionIdentity mapToTransactionIdentity(TransactionEntity transactionEntity);

}
