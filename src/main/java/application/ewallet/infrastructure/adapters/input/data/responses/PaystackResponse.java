package application.ewallet.infrastructure.adapters.input.data.responses;

import lombok.*;

@Setter
@Getter
@ToString
public class PaystackResponse {
    private boolean status;
    private String message;
    private PaystackResponse data;


    @Setter
    @Getter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PayStackResponses {
        private String authorization_url;
        private String access_code;
        private String reference;
    }
}
