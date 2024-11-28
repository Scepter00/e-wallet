package application.ewallet.domain.models;

import application.ewallet.domain.enums.WalletStatus;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WalletIdentity {
    private String id;
    private String userId;
    private BigDecimal balance;
    private String createdAt;
    private String updatedAt;
    private String createdBy;
    private WalletStatus walletStatus;
    @DocumentReference
    private List<TransactionIdentity> transactionIdentity;
}
