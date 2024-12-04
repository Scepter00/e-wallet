package application.ewallet.infrastructure.adapters.input.data.responses.premblyResponse;

import lombok.*;

@Setter
@Getter
@Builder
@ToString
public class PremblyNinsResponse {
    private String id;
    private String userId;
    private String firstname;
    private String lastname;
    private String imageUrl;
    private String nin;
    private boolean verified;
}
