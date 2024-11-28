package application.ewallet.domain.models;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IdentityVerification {
    private String nin;
    private String identityImage;
}
