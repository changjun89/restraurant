package me.changjun.restaurant.interfaces;

import me.changjun.restaurant.application.RestaurantService;
import me.changjun.restaurant.domain.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping(value = "/api/restaurants")
    public List<Restaurant> getRestaurants(@RequestParam("region") String region) {
        List<Restaurant> restaurants = restaurantService.getRestaurants(region);
        return restaurants;
    }

    @GetMapping(value = "/api/restaurants/{id}")
    public Restaurant getRestaurants(@PathVariable(name = "id") Long id) {
        Restaurant restaurant = restaurantService.getRestaurantById(id);
        return restaurant;
    }
}
