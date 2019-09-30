package me.changjun.restaurant.domain;

public class RestaurantNotFoundException extends RuntimeException {

    public RestaurantNotFoundException(long id) {
        super("Could Not find Restaurant id : " + id);
    }
}
