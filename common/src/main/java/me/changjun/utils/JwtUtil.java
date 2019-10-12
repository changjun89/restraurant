package me.changjun.utils;

import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    public String createToken(long id, String name) {
        return "header.payload.signature";
    }
}
