package application.ewallet.infrastructure.adapters.input.data.responses.premblyResponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class PremblyLivelinessResponse extends PremblyResponse {

    @JsonProperty("status")
    private boolean status;

    @JsonProperty("detail")
    private String detail;

    @JsonProperty("response_code")
    private String responseCode;

    @JsonProperty("confidence")
    private double confidence;

    @JsonProperty("confidence_in_percentage")
    private double confidenceInPercentage;

    @JsonProperty("verication")
    private Verification verification;

    @JsonProperty("widget_info")
    private Object widgetInfo;

    @JsonProperty("session")
    private Object session;
}
