package me.changjun.utils;

import io.jsonwebtoken.Claims;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JwtUtilTest {

    private static final String SECRET = "12345678901234567890123456789012";

    private JwtUtil jwtUtil;

    @Before
    public void setup() {
        jwtUtil = new JwtUtil(SECRET);
    }

    @Test
    public void createToken() {
        String token = jwtUtil.createToken(1L, "Changjun");

        assertThat(token).contains(".");
    }

    @Test
    public void getClaims() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjEsIm5hbWUiOiJDaGFuZ2p1biJ9.9y_qfcOH9PlX6942ajVOpMs6ateGYL3OCuyUU9-uByw";
        Claims claims = jwtUtil.getClaims(token);

        assertThat(claims.get("name")).isEqualTo("Changjun");
        assertThat(claims.get("userId", Long.class)).isEqualTo(1L);
    }
}
