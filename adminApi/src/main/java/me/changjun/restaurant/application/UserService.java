package me.changjun.restaurant.application;

import me.changjun.restaurant.domain.User;
import me.changjun.restaurant.domain.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User addUser(User resource) {
        return resource;
    }

    public User addUser(String email, String name) {
        User user = User.builder()
                .name(name)
                .email(email)
                .build();
        return userRepository.save(user);
    }
}
