package application.ewallet.infrastructure.adapters.input.data.responses;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {

    private String message;

    private T data;

    private String statusCode;
}
