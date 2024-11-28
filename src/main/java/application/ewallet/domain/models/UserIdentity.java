package application.ewallet.domain.models;

import application.ewallet.domain.enums.UserRole;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserIdentity {
    private String id;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private UserRole userRole;
    @DocumentReference
    private WalletIdentity walletIdentity;
    private boolean enabled;
    private boolean emailVerified;
    private String password;
    private String createdAt;
    private String createdBy;
    private String accessToken;
    private String refreshToken;
}
