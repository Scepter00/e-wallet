package application.ewallet.infrastructure.adapters.input.data.responses.premblyResponse;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Data
@Builder
@Slf4j
public class Verification {

    @JsonProperty("status")
    private String status;
    private boolean validNin;
    @JsonProperty("reference")
    private String reference;

    public void updateValidNin() {
        if (StringUtils.equalsIgnoreCase("VERIFIED", this.status)) {
            validNin = Boolean.TRUE;
        }
    }
}
