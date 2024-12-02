package application.ewallet.infrastructure.adapters.input.data.request;

import application.ewallet.domain.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserIdentityRequest {

    private String id;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @Email(message = "Email is required")
    @NotBlank(message = "Email cannot be null, empty, or contain only spaces")
    private String email;

    @NotNull(message = "Role is required")
    private UserRole userRole;

    @NotBlank(message = "Password is required")
    private String password;
}
