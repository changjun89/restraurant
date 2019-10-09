package me.changjun.restaurant.interfaces;

import me.changjun.restaurant.application.UserService;
import me.changjun.restaurant.domain.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

    //1. User list
    //2. User create -> 회원가입
    //3. User update ->
    //4. User delete -> level : 0 => 아무것도 못함
    //(1: customer , 2: restaurant owner 3: admin)
}
