package me.changjun.restaurant.application;

import me.changjun.restaurant.domain.User;
import me.changjun.restaurant.domain.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

public class UserServiceTest {

    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        userService = new UserService(userRepository);
    }

    @Test
    public void users() {
        List<User> mockUsers = new ArrayList<>();
        User mockUser = User.builder()
                .name("changjun")
                .email("leechang0423@naver.com")
                .level(3)
                .build();
        mockUsers.add(mockUser);
        given(userRepository.findAll()).willReturn(mockUsers);

        List<User> users = userService.getUsers();
        User user = users.get(0);

        assertThat(user.getName()).isEqualTo("changjun");
        assertThat(user.getEmail()).isEqualTo("leechang0423@naver.com");
        assertThat(user.getLevel()).isEqualTo(3);
    }
}
