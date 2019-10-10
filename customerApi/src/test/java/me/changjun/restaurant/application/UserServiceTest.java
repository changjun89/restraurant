package me.changjun.restaurant.application;

import me.changjun.restaurant.domain.User;
import me.changjun.restaurant.domain.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
    public void create() {
        //given
        String email = "leechang0423@naver.com";
        String name = "changjun";
        String password = "password";

        User mockUser = User.builder()
                .email(email)
                .name(name)
                .password(password)
                .build();
        given(userRepository.save(any())).willReturn(mockUser);
        //when
        User user = userService.create(email, name, password);
        //then
        verify(userRepository).save(any());
        assertThat(user.getName()).isEqualTo("changjun");
        assertThat(user.getEmail()).isEqualTo("leechang0423@naver.com");
        assertThat(user.getPassword()).isEqualTo(password);
    }
}
