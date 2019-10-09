package me.changjun.restaurant.application;

import me.changjun.restaurant.domain.User;
import me.changjun.restaurant.domain.UserNotFoundException;
import me.changjun.restaurant.domain.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
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

    public User updateUser(Long id, String email, String name, int level) {
        User user = userRepository.findById(id).orElseThrow(()-> new UserNotFoundException(id));
        User updatedUser = user.updateInfo(email, name, level);
        return updatedUser;
    }

    public User deActiveUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(()-> new UserNotFoundException(id));
        user.deActive();
        return user;
    }
}
