package me.changjun.restaurant.application;

public class EmailNotExistedException extends RuntimeException{
    public EmailNotExistedException(String email) {
        super("email not existed :"+email);
    }
}
