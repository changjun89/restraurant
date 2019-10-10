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
        assertThat(user.isActive()).isTrue();

    }

    @Test
    public void deActive() {
        //given
        User user = User.builder()
                .id(1L)
                .email("leechang0423@naver.com")
                .name("changjun")
                .level(100)
                .build();
        //when
        user.deActive();
        //then
        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getLevel()).isEqualTo(0);
        assertThat(user.isActive()).isFalse();
    }

    @Test
    public void accessTokenWithPassword() {
        User user = User.builder().password("ACCESSTOKEN").build();

        assertThat(user.getAccessToken()).isEqualTo("ACCESSTOKE");
    }

    @Test
    public void accessTokenWithNoPassword() {
        User user = User.builder().build();

        assertThat(user.getAccessToken()).isEqualTo("");
    }
}
