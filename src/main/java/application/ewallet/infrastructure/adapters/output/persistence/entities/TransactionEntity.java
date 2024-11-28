package application.ewallet.infrastructure.adapters.output.persistence.entities;

import application.ewallet.domain.enums.TransactionType;
import application.ewallet.domain.enums.TransactionStatus;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "transactions")
public class TransactionEntity {
    @Id
    private String id;
    private String walletId;
    private BigDecimal amount;
    private String description;
    private TransactionType transactionType;
    private TransactionStatus transactionStatus;
    private LocalDateTime createdAt;
}
