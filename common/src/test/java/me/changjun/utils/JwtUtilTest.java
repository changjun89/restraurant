package me.changjun.utils;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JwtUtilTest {

    @Test
    public void createToken() {
        String secret = "12345678901234567890123456789012";
        JwtUtil jwtUtil = new JwtUtil(secret);
        String token = jwtUtil.createToken(1L,"Changjun");

        assertThat(token).contains(".");
    }
}
