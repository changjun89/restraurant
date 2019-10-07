package me.changjun.restaurant.application;

import me.changjun.restaurant.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final MenuItemRepository menuItemRepository;
    private final ReviewRepository reviewRepository;

    public RestaurantService(RestaurantRepository restaurantRepository, MenuItemRepository menuItemRepository, ReviewRepository reviewRepository) {
        this.restaurantRepository = restaurantRepository;
        this.menuItemRepository = menuItemRepository;
        this.reviewRepository = reviewRepository;
    }

    public List<Restaurant> getRestaurants(String region) {
        List<Restaurant> restaurants = restaurantRepository.findAllByLocationContaining(region);

        for (Restaurant restaurant : restaurants) {
            restaurant.setMenuItem(menuItemRepository.findAllByRestaurantId(restaurant.getId()));
        }
        return restaurants;

    }

    public Restaurant getRestaurantById(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(() -> new RestaurantNotFoundException(id));

        List<MenuItem> menuItems = menuItemRepository.findAllByRestaurantId(id);
        List<Review> reviews = reviewRepository.findAllByRestaurantId(id);
        restaurant.setMenuItem(menuItems);
        restaurant.setReviews(reviews);

        return restaurant;
    }
}
