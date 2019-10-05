package me.changjun.restaurant.application;

import me.changjun.restaurant.domain.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

public class RestaurantServiceTest {

    private RestaurantService restaurantService;

    @Mock
    private RestaurantRepository restaurantRepository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockRestaurantRepository();
        restaurantService = new RestaurantService(restaurantRepository);
    }

    private void mockRestaurantRepository() {
        List<Restaurant> restaurants = new ArrayList<>();
        Restaurant restaurant = Restaurant.builder()
                .id(1L)
                .name("Bob zip")
                .location("seoul").build();
        restaurant.setMenuItem(Arrays.asList(new MenuItem("Kimchi")));
        restaurants.add(restaurant);
        given(restaurantRepository.findAll()).willReturn(restaurants);
        given(restaurantRepository.findById(1L)).willReturn(Optional.of(restaurant));

    }

    @Test
    public void getRestaurants() {
        List<Restaurant> restaurants = restaurantService.getRestaurants();
        assertThat(restaurants.get(0).getId()).isEqualTo(1L);

        List<MenuItem> menuItems = restaurants.get(0).getMenuItem();
        assertThat(menuItems.get(0).getName()).isEqualTo("Kimchi");
    }

    @Test(expected = RestaurantNotFoundException.class)
    public void getRestaurantByIdNotWithExisted() {
        restaurantService.getRestaurantById(404L);
    }

    @Test
    public void getRestaurantByIdWithExisted() {
        Restaurant restaurant = restaurantService.getRestaurantById(1L);
        assertThat(restaurant.getId()).isEqualTo(1L);
    }

    @Test
    public void addRestaurant() {
        given(restaurantRepository.save(any())).will(invocationOnMock ->
        {
            Restaurant restaurant = invocationOnMock.getArgument(0);
            restaurant.setId(1L);
            return restaurant;
        });

        Restaurant restaurant = Restaurant.builder().name("비룡").location("서울").build();
        Restaurant newRestaurant = restaurantService.addRestaurant(restaurant);

        assertThat(newRestaurant.getId()).isEqualTo(1L);
    }

    @Test
    public void updateRestaurants() {
        Restaurant restaurant = Restaurant.builder().id(1L).name("밥집").location("서울").build();
        given(restaurantRepository.findById(1L)).willReturn(Optional.of(restaurant));

        restaurantService.updateRestaurants(1L, "술집", "부산");

        assertThat(restaurant.getName()).isEqualTo("술집");
        assertThat(restaurant.getLocation()).isEqualTo("부산");
    }
}
