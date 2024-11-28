package application.ewallet.infrastructure.adapters.output.persistence.entities;

import application.ewallet.domain.enums.WalletStatus;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "wallets")
public class WalletEntity {
    private String id;
    private String userId;
    private BigDecimal balance;
    private String createdAt;
    private String updatedAt;
    private String createdBy;
    private WalletStatus walletStatus;
    @DocumentReference
    private List<TransactionEntity> transactionEntities;
}
