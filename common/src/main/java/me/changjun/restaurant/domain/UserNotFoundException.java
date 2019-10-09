package me.changjun.restaurant.domain;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Long id) {
        super("Could Not find Restaurant id : " + id);
    }
}
