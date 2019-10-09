package me.changjun.restaurant.application;

import me.changjun.restaurant.domain.User;
import me.changjun.restaurant.domain.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

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

    @Test
    public void addUser() {
        String email = "leechang0423@naver.com";
        String name = "changjun";
        User mockUser = User.builder().email(email).name(name).build();
        given(userRepository.save(any())).willReturn(mockUser);

        User user = userService.addUser(email, name);

        verify(userRepository).save(any());
        assertThat(user.getName()).isEqualTo("changjun");
        assertThat(user.getEmail()).isEqualTo("leechang0423@naver.com");
    }

    @Test
    public void updateUser() {
        Long id = 1L;
        String email = "leechang0423@naver.com";
        String name = "changjun";
        int level = 100;

        User mockUser = User.builder()
                .id(id)
                .name("superMan")
                .email("leechang0423@gamil.com")
                .level(50)
                .build();

        given(userRepository.findById(id)).willReturn(Optional.of(mockUser));

        User user = userService.updateUser(id, email, name, level);

        verify(userRepository).findById(id);

        assertThat(user.getId()).isEqualTo(id);
        assertThat(user.getEmail()).isEqualTo(email);
        assertThat(user.getName()).isEqualTo(name);
        assertThat(user.getLevel()).isEqualTo(level);
    }

    @Test
    public void deActiveUser() {
        Long id = 1L;
        User mockUser = User.builder()
                .id(id)
                .name("changjun")
                .level(100)
                .build();

        given(userRepository.findById(id)).willReturn(Optional.ofNullable(mockUser));

        User user = userService.deActiveUser(id);

        verify(userRepository).findById(id);
        assertThat(user.getId()).isEqualTo(id);
        assertThat(user.getLevel()).isEqualTo(0);

    }
}
