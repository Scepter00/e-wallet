package application.ewallet.infrastructure.adapters.input.data.responses;

import lombok.*;

@Setter
@Getter
@ToString
public class PaystackResponse {
    private boolean status;
    private String message;
    private PaystackResponses data;


    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @lombok.ToString

    public static class PaystackResponses{
        private String authorization_url;
        private String access_code;
        private String reference;
    }
}
