package demo.authservice.service;

import demo.authservice.dto.request.SignInRequest;
import demo.authservice.dto.response.JwtAuthenticationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthorizationService {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public JwtAuthenticationResponse signing(SignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        ));

        var user = userService.userDetailsService().loadUserByUsername(request.getEmail());
        log.info("User with email: {} signing", request.getEmail());
        return JwtAuthenticationResponse.builder()
                .accessToken(jwtService.generateToken(user))
                .build();
    }
}
