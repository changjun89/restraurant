package me.changjun.restaurant.application;

public class PasswordWrongException extends RuntimeException {
    public PasswordWrongException() {
        super("Password is Wrong");
    }
}
