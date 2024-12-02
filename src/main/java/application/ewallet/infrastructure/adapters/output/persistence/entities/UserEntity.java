package application.ewallet.infrastructure.adapters.output.persistence.entities;

import application.ewallet.domain.enums.UserRole;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Setter
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class UserEntity {
    private String id;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private UserRole userRole;
    private boolean enabled;
    private boolean emailVerified;
    private String password;
    private String createdAt;
    @DocumentReference
    private WalletEntity walletEntity;
}
