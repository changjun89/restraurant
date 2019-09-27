package me.changjun.restaurant.domain;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RestaurantRepositoryImpl implements RestaurantRepository {

    private List<Restaurant> restaurants = new ArrayList<>();

    public RestaurantRepositoryImpl() {
        restaurants.add(new Restaurant(1L, "Bob zip", "seoul"));
        restaurants.add(new Restaurant(20L, "Cyber Food", "seoul"));
    }

    @Override
    public List<Restaurant> findAll() {
        return restaurants;
    }

    @Override
    public Restaurant findById(Long id) {
        return restaurants.stream()
                .filter(restaurant -> restaurant.getId().equals(id))
                .findFirst()
                .get();
    }

    @Override
    public Restaurant save(Restaurant restaurant) {
        restaurants.add(restaurant);
        restaurant.setId(1L);
        return restaurant;
    }
}
