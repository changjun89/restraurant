package me.changjun.restaurant.interfaces;

import me.changjun.restaurant.application.RestaurantService;
import me.changjun.restaurant.domain.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping(value = "/api/restaurants")
    public List<Restaurant> getRestaurants() {
        List<Restaurant> restaurants = restaurantService.getRestaurants();
        return restaurants;
    }

    @GetMapping(value = "/api/restaurants/{id}")
    public Restaurant getRestaurants(@PathVariable(name = "id") Long id) {
        Restaurant restaurant = restaurantService.getRestaurantById(id);
        return restaurant;
    }

    @PostMapping(value = "/api/restaurants")
    public ResponseEntity<?> create(@RequestBody Restaurant resource) throws URISyntaxException {
        Restaurant created = restaurantService.addRestaurant(resource);
        URI uri = new URI("/api/restaurants/" + created.getId());
        return ResponseEntity.created(uri).body("{}");
    }

    @PatchMapping(value = "/api/restaurants/{id}")
    public String update(@PathVariable("id") Long id, @RequestBody Restaurant resource) {
        restaurantService.updateRestaurants(id, resource.getName(), resource.getLocation());
        return "{}";
    }
}
