package me.changjun.restaurant.interfaces;

import me.changjun.restaurant.application.UserService;
import me.changjun.restaurant.domain.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/api/users")
    public List<User> list() {
        return userService.getUsers();
    }

    @PostMapping("/api/users")
    public ResponseEntity create(@RequestBody User resource) throws URISyntaxException {
        String email = resource.getEmail();
        String name = resource.getName();

        User user = userService.addUser(email, name);
        URI uri = new URI("/api/user/" + user.getId());
        return ResponseEntity.created(uri).body("{}");
    }

    @PatchMapping("/api/users/{id}")
    public User update(@PathVariable("id") Long id, @RequestBody User resource) throws URISyntaxException {
        String email = resource.getEmail();
        String name = resource.getName();
        int level = resource.getLevel();

        return userService.updateUser(id, email, name, level);
    }

    @DeleteMapping("/api/users/{id}")
    public void delete(@PathVariable("id") Long id) {
        userService.deActiveUser(id);
    }

}
