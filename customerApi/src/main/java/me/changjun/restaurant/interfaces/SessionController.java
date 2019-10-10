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
public class SessionController {

    private final UserService userService;

    public SessionController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/api/session")
    public ResponseEntity<SessionResponseDto> create(@RequestBody SessionRequestDto resource) throws URISyntaxException {
        String accessToken = "ACCESSTOKEN";
        User user = userService.authenticate(resource.getEmail(), resource.getPassword());

        URI uri = new URI("/api/session");
        return ResponseEntity.created(uri).body(
                SessionResponseDto.builder()
                        .accessToken(user.getAccessToken())
                        .build()
        );
    }

}
