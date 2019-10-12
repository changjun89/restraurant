package me.changjun.utils;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JwtUtilTest {

    @Test
    public void createToken() {
        JwtUtil jwtUtil = new JwtUtil();
        String token = jwtUtil.createToken(1L,"Changjun");

        assertThat(token).contains(".");
    }
}
