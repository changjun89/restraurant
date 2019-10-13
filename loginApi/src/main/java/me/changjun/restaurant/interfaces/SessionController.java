package me.changjun.restaurant.interfaces;

import me.changjun.restaurant.application.UserService;
import me.changjun.restaurant.domain.User;
import me.changjun.utils.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class SessionController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public SessionController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/api/session")
    public ResponseEntity<SessionResponseDto> create(@RequestBody SessionRequestDto resource) throws URISyntaxException {
        User user = userService.authenticate(resource.getEmail(), resource.getPassword());

        String accessToken = jwtUtil.createToken(user.getId(), user.getName()
                , user.isRestaurantOwner() ? user.getRestaurantId() : null);
        URI uri = new URI("/api/session");
        return ResponseEntity.created(uri).body(
                SessionResponseDto.builder()
                        .accessToken(accessToken)
                        .build()
        );
    }

}
