package demo.authservice.controller;

import demo.authservice.dto.request.SignInRequest;
import demo.authservice.dto.request.SignupInitiateRequest;
import demo.authservice.dto.request.SignupResendCodeRequest;
import demo.authservice.dto.request.SignupVerifyRequest;
import demo.authservice.dto.response.JwtAuthenticationResponse;
import demo.authservice.dto.response.SignupInitiateResponse;
import demo.authservice.service.AuthorizationService;
import demo.authservice.service.RegistrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authorization controller", description = "API for authorization users in system")
public class AuthorizationController {

    private final RegistrationService registrationService;
    private final AuthorizationService authorizationService;

    @Operation(summary = "Start signup", description = "Submits a registration request and is responsible for sending the cod to email")
    @PostMapping("/signup/initialization")
    public ResponseEntity<SignupInitiateResponse> signupInitiate(@RequestBody @Valid SignupInitiateRequest request) {
        SignupInitiateResponse response = registrationService.signupInitiate(request);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Verification of registration uses confirmation code")
    @PostMapping("/signup/verification")
    public ResponseEntity<JwtAuthenticationResponse> signupVerify(@RequestBody @Valid SignupVerifyRequest request) {
        JwtAuthenticationResponse response = registrationService.signupVerify(request);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Resend code", description = "It is needed for sending new confirmation code")
    @PostMapping("/signup/verification/resend")
    public ResponseEntity<?> signupResendCode(@RequestBody @Valid SignupResendCodeRequest request) {
        registrationService.resendCode(request.getEmail());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Sign in", description = "Sign in and getting access token")
    @PostMapping("/signing")
    public ResponseEntity<JwtAuthenticationResponse> signing(@RequestBody @Valid SignInRequest request) {
        JwtAuthenticationResponse jwt = authorizationService.signing(request);
        return ResponseEntity.ok().body(jwt);
    }

}
