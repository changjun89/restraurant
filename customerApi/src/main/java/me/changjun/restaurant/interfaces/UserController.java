package me.changjun.restaurant.interfaces;

import me.changjun.restaurant.application.UserService;

import me.changjun.restaurant.domain.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/api/users")
    public ResponseEntity creation(@RequestBody User resource) throws URISyntaxException {
        String email = resource.getEmail();
        String name = resource.getName();
        String password = resource.getPassword();

        User user = userService.create(email,name,password);
        URI uri = new URI("/api/users/" + user.getId());
        return ResponseEntity.created(uri).body("{}");
    }

}
