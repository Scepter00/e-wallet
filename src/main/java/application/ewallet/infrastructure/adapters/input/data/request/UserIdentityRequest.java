package application.ewallet.infrastructure.adapters.input.data.request;

import application.ewallet.domain.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserIdentityRequest {
    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @Email(message = "Email is required")
    private String email;

    @NotNull(message = "Role is required")
    private UserRole userRole;

    @NotBlank(message = "Password is required")
    private String password;
}
