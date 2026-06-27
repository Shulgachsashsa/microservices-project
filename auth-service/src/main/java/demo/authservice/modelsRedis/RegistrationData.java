package demo.authservice.modelsRedis;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import org.springframework.data.annotation.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Registration data redis")
public class RegistrationData implements Serializable {

    @Serial
    private static final long serialVersionUID = 1;

    @Id
    @Schema(description = "Email", example = "example@gmail.com")
    @NotBlank(message = "Email can not is empty")
    @Email(message = "Email must be in format example@gmail.com")
    private String email;

    @Schema(description = "Password", example = "1wdt12d13")
    @Size(min = 8, max = 60, message = "Password must be between 8 and 60 characters long")
    @NotBlank(message = "Password can not be is empty")
    private String password;

    @Schema(description = "Confirmation code which sent to email")
    @Size(min = 6, max = 6, message = "Verification code must be of 6 characters long")
    private String verificationCode;

    @Schema(description = "Counter attempts input confirmation code")
    private int attempts;
}
