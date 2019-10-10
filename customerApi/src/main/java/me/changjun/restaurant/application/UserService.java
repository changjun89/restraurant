package me.changjun.restaurant.application;

import me.changjun.restaurant.domain.User;
import me.changjun.restaurant.domain.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User create(String email, String name, String password) {
        Optional<User> existed = userRepository.findByEmail(email);
        if (existed.isPresent()) {
            throw new EmailExistedException(email);
        }

        String encodedPassword = passwordEncoder.encode(password);
        User user = User.builder()
                .name(name)
                .email(email)
                .password(encodedPassword)
                .build();

        return userRepository.save(user);
    }


    public User authenticate(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new EmailNotExistedException(email));
        if(!passwordEncoder.matches(password, user.getPassword())) {
            throw new PasswordWrongException();
        }
        return user;
    }
}
