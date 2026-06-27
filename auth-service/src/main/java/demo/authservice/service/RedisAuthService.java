package demo.authservice.service;

import demo.authservice.modelsRedis.RegistrationData;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisAuthService {

    @Value("${spring.cache.redis.key-prefix}")
    private String KEY_PREFIX;

    @Value("${spring.cache.redis.time-to-live}")
    private long TTL_MINUTES;

    @Value("${spring.cache.redis.max-attempts}")
    private int MAX_ATTEMPTS;

    private final RedisTemplate<String, Object> redisTemplate;

    public void addInitiateRegistrationData(String key, Object o) {
        redisTemplate.opsForValue().set(key + KEY_PREFIX, o, TTL_MINUTES, TimeUnit.MINUTES);
    }

    public void deleteRecord(String email) {
        redisTemplate.delete(email + KEY_PREFIX);
    }

    public RegistrationData getRegistrationData(String email) {
        return (RegistrationData) redisTemplate.opsForValue().get(email + KEY_PREFIX);
    }

    public boolean checkMaxAttempts(String email) throws NullPointerException {
        return getRegistrationData(email).getAttempts() >= MAX_ATTEMPTS;
    }

    public void incrementAttemptByEmail(String email) {
        RegistrationData data = getRegistrationData(email);
        data.setAttempts(data.getAttempts() + 1);
        addInitiateRegistrationData(email, data);
    }

    public boolean equalsCode(String email, String code) {
        RegistrationData data = getRegistrationData(email);
        return data.getVerificationCode().equals(code);
    }

}
