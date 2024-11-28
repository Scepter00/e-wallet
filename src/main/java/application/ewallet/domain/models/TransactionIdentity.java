package application.ewallet.domain.models;

import application.ewallet.domain.enums.TransactionType;
import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TransactionIdentity {
    private String id;
    private String walletId;
    private BigDecimal amount;
    private String payStackReference;
    private TransactionType transactionType;
    private String description;
    private String createdAt;
}
