package demo.authservice.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Signup initiate response")
public class SignupInitiateResponse {

    @Size(min = 4, max = 60, message = "Email must be between 4 and 60 characters long")
    @NotBlank(message = "Email can not be is empty")
    @Email(message = "Email must be in format example@gmail.com")
    @Schema(description = "Email", example = "example@gmail.com")
    private String email;

    @NotBlank(message = "Message can not be is empty")
    @Schema(description = "Message", example = "123456")
    private String message;
}
