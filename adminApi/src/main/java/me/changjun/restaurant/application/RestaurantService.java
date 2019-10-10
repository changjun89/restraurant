package me.changjun.restaurant.application;

import me.changjun.restaurant.domain.Restaurant;
import me.changjun.restaurant.domain.RestaurantNotFoundException;
import me.changjun.restaurant.domain.RestaurantRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;

    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public List<Restaurant> getRestaurants() {
        return restaurantRepository.findAll();
    }

    public Restaurant getRestaurantById(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(() -> new RestaurantNotFoundException(id));
        return restaurant;
    }

    public Restaurant addRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    @Transactional
    public Restaurant updateRestaurants(Long id, String name, String location) {
        Restaurant restaurant = restaurantRepository.findById(id).orElse(null);
        Restaurant updatedRestaurant = restaurant.updateInfo(name, location);
        return updatedRestaurant;
    }
}
