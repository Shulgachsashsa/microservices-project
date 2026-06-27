package demo.authservice.service;

import demo.authservice.entity.UserEntity;
import demo.authservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Data
public class UserService {

    private final UserRepository userRepository;

    public UserDetailsService userDetailsService() {
        return this::getByEmail;
    }

    public UserEntity getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void saveUser(UserEntity user) { userRepository.save(user); }

}
