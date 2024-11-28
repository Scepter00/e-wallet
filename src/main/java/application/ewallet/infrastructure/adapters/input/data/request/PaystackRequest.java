package application.ewallet.infrastructure.adapters.input.data.request;

import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaystackRequest {
    private String email;
    private BigDecimal amount;
}
