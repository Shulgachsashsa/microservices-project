package demo.authservice.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(name = "Signup initiate request")
public class SignupInitiateRequest {

    @Size(min = 4, max = 60, message = "Email must be between 4 and 60 characters long")
    @NotBlank(message = "Email can not be is empty")
    @Email(message = "Email must be in format example@gmail.com")
    @Schema(description = "Email", example = "example@gmail.com")
    private String email;

    @Size(min = 8, max = 60, message = "Password must be between 8 and 60 characters long")
    @NotBlank(message = "Password can not be is empty")
    @Schema(description = "Password", example = "1g23y2jd3")
    private String password;
}
