package me.changjun.restaurant.interfaces;

import me.changjun.restaurant.domain.Restaurant;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestaurantController {

    @GetMapping(value = "/api/restaurants")
    public Restaurant getRestaurants() {
        Restaurant restaurant = new Restaurant(1L, "Bob zip", "seoul");
        return restaurant;
    }
}
