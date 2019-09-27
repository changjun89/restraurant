package me.changjun.restaurant.domain;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class RestaurantRepositoryImplTest {

    private RestaurantRepository restaurantRepository;

    @Test
    public void save() {
        restaurantRepository = new RestaurantRepositoryImpl();

        int oldCount = restaurantRepository.findAll().size();
        Restaurant restaurant = new Restaurant("비룡", "부산");
        restaurantRepository.save(restaurant);

        assertThat(restaurant.getId()).isEqualTo(1L);

        int newCount = restaurantRepository.findAll().size();

        assertThat(newCount - oldCount).isEqualTo(1);
    }
}
