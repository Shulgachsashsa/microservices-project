package demo.authservice.service;

import demo.authservice.dto.request.SignupInitiateRequest;
import demo.authservice.dto.request.SignupVerifyRequest;
import demo.authservice.dto.response.JwtAuthenticationResponse;
import demo.authservice.dto.response.SignupInitiateResponse;
import demo.authservice.entity.UserEntity;
import demo.authservice.exceptions.IncorrectVerificationCodeException;
import demo.authservice.exceptions.MaxLimitedAttemptsException;
import demo.authservice.exceptions.RegistrationExpiredOrNotFoundException;
import demo.authservice.exceptions.UserWithEmailAlreadyExists;
import demo.authservice.modelsRedis.RegistrationData;
import demo.common.roles.Role;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegistrationService {

    private final RedisAuthService redisAuthService;
    private final UserService userService;
    private final MailCodeService mailCodeService;
    private final MailSendService mailSendService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Transactional
    public SignupInitiateResponse signupInitiate(SignupInitiateRequest request) {
        log.info("User with email: {} initiated registration", request.getEmail());

        if (userService.getByEmail(request.getEmail()) != null) {
            throw new UserWithEmailAlreadyExists("User with email: " + request.getEmail() + " already exists");
        }

        String code = mailCodeService.generateCode();

        RegistrationData data = RegistrationData.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .verificationCode(code)
                .build();

        redisAuthService.addInitiateRegistrationData(request.getEmail(), data);
        mailSendService.send(request.getEmail(), code);

        log.info("Confirmation code was sent to email: {}", request.getEmail());
        return new SignupInitiateResponse(request.getEmail(), code);
    }

    @Transactional
    public JwtAuthenticationResponse signupVerify(SignupVerifyRequest request) {
        log.info("User with email: {} started confirmation own account", request.getEmail());

        RegistrationData data = redisAuthService.getRegistrationData(request.getEmail());

        if (data == null) {
            throw new RegistrationExpiredOrNotFoundException("Time registration was expired or record with registration was not found");
        }

        if (redisAuthService.checkMaxAttempts(request.getEmail())) {
            redisAuthService.deleteRecord(request.getEmail());
            throw new MaxLimitedAttemptsException("Max limit attempts");
        }

        if (!redisAuthService.equalsCode(request.getEmail(), data.getVerificationCode())) {
            redisAuthService.incrementAttemptByEmail(request.getEmail());
            throw new IncorrectVerificationCodeException("Incorrect input verification code: " + request.getCode());
        }

        UserEntity user = UserEntity.builder()
                .email(data.getEmail())
                .password(data.getPassword())
                .role(Role.ROLE_USER)
                .build();

        userService.saveUser(user);
        redisAuthService.deleteRecord(request.getEmail());

        log.info("User with email: {} registry successful", request.getEmail());
        return JwtAuthenticationResponse
                .builder()
                .accessToken(jwtService.generateToken(user))
                .build();
    }

    @Transactional
    public void resendCode(String email) {
        RegistrationData data = (RegistrationData) redisAuthService.getRegistrationData(email);

        if (data == null) {
            throw new RegistrationExpiredOrNotFoundException("Record with registration data was not found");
        }

        String code = mailCodeService.generateCode();

        data.setVerificationCode(code);
        data.setAttempts(0);
        redisAuthService.deleteRecord(email);
        redisAuthService.addInitiateRegistrationData(email, data);

        mailSendService.send(email, code);
        log.info("New confirmation code was sent users with email: {}", email);
    }

}
