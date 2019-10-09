package me.changjun.restaurant.domain;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTest {

    @Test
    public void creation() {
        //given
        String name = "테스터";
        String email = "leechang0423@naver.com";
        int level = 3;

        //when
        User user = User.builder()
                .email(email)
                .name(name)
                .level(level)
                .build();
        //then
        assertThat(user.getName()).isEqualTo(name);
        assertThat(user.getEmail()).isEqualTo(email);
        assertThat(user.getLevel()).isEqualTo(level);
        assertThat(user.isAdmin()).isFalse();

    }
}
