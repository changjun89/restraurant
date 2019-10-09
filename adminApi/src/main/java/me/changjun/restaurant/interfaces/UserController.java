package me.changjun.restaurant.interfaces;

import me.changjun.restaurant.application.UserService;
import me.changjun.restaurant.domain.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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

    //1. User list
    //2. User create -> 회원가입
    //3. User update ->
    //4. User delete -> level : 0 => 아무것도 못함
    //(1: customer , 2: restaurant owner 3: admin)
}
