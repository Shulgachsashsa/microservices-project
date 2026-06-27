package demo.authservice.entity;

import demo.common.roles.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "User entity")
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Schema(description = "Unique identifier of user")
    private Long id;

    @Column(name = "email")
    @Schema(description = "Email", example = "example@gmail.com")
    @Size(min = 4, max = 60, message = "Email must be between 4 and 60 characters long")
    @NotBlank(message = "Email can not be is empty")
    @Email(message = "Email must be in format example@gmail.com")
    private String email;

    @Column(name = "password")
    @Size(min = 8, max = 40, message = "Password must be between 8 and 40 characters long")
    @NotBlank(message = "Password can not be is empty")
    @Schema(description = "Password", example = "1g23y2jd3")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    @Schema(description = "Role of user in system", example = "ROLE_USER")
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
