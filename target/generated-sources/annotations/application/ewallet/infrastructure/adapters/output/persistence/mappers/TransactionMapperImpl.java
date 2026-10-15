package application.ewallet.infrastructure.adapters.output.persistence.mappers;

import application.ewallet.domain.models.TransactionIdentity;
import application.ewallet.infrastructure.adapters.output.persistence.entities.TransactionEntity;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-10-14T13:51:32+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.11 (Microsoft)"
)
@Component
public class TransactionMapperImpl implements TransactionMapper {

    @Override
    public TransactionEntity mapToTransactionEntity(TransactionIdentity transactionIdentity) {
        if ( transactionIdentity == null ) {
            return null;
        }

        TransactionEntity.TransactionEntityBuilder transactionEntity = TransactionEntity.builder();

        transactionEntity.id( transactionIdentity.getId() );
        transactionEntity.walletId( transactionIdentity.getWalletId() );
        transactionEntity.amount( transactionIdentity.getAmount() );
        transactionEntity.description( transactionIdentity.getDescription() );
        transactionEntity.transactionType( transactionIdentity.getTransactionType() );
        if ( transactionIdentity.getCreatedAt() != null ) {
            transactionEntity.createdAt( LocalDateTime.parse( transactionIdentity.getCreatedAt() ) );
        }

        return transactionEntity.build();
    }

    @Override
    public TransactionIdentity mapToTransactionIdentity(TransactionEntity transactionEntity) {
        if ( transactionEntity == null ) {
            return null;
        }

        TransactionIdentity.TransactionIdentityBuilder transactionIdentity = TransactionIdentity.builder();

        transactionIdentity.id( transactionEntity.getId() );
        transactionIdentity.walletId( transactionEntity.getWalletId() );
        transactionIdentity.amount( transactionEntity.getAmount() );
        transactionIdentity.transactionType( transactionEntity.getTransactionType() );
        transactionIdentity.description( transactionEntity.getDescription() );
        if ( transactionEntity.getCreatedAt() != null ) {
            transactionIdentity.createdAt( DateTimeFormatter.ISO_LOCAL_DATE_TIME.format( transactionEntity.getCreatedAt() ) );
        }

        return transactionIdentity.build();
    }
}
