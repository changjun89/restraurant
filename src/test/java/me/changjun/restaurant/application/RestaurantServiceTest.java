package me.changjun.restaurant.application;

import me.changjun.restaurant.domain.MenuItem;
import me.changjun.restaurant.domain.MenuItemRepository;
import me.changjun.restaurant.domain.Restaurant;
import me.changjun.restaurant.domain.RestaurantRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

public class RestaurantServiceTest {

    private RestaurantService restaurantService;

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private MenuItemRepository menuItemRepository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        mockRestaurantRepository();
        mockMenuItemRepository();

        restaurantService = new RestaurantService(restaurantRepository, menuItemRepository);
    }

    private void mockMenuItemRepository() {
        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItem("Kimchi"));
        given(menuItemRepository.findAllByRestaurantId(1L)).willReturn(menuItems);
    }

    private void mockRestaurantRepository() {
        List<Restaurant> restaurants = new ArrayList<>();
        Restaurant restaurant = new Restaurant(1L, "Bob zip", "seoul");
        restaurant.addMenuItem(new MenuItem("Kimchi"));
        restaurants.add(restaurant);
        given(restaurantRepository.findAll()).willReturn(restaurants);
        given(restaurantRepository.findById(1L)).willReturn(Optional.of(restaurant));

    }

    @Test
    public void getRestaurants() {
        List<Restaurant> restaurants = restaurantService.getRestaurants();
        assertThat(restaurants.get(0).getId()).isEqualTo(1L);

        List<MenuItem> menuItems = restaurants.get(0).getMenuItems();
        assertThat(menuItems.get(0).getName()).isEqualTo("Kimchi");
    }

    @Test
    public void getRestaurantById() {
        Restaurant restaurant = restaurantService.getRestaurantById(1L);
        assertThat(restaurant.getId()).isEqualTo(1L);

        String menuItemName = restaurant.getMenuItems().get(0).getName();
        assertThat(menuItemName).isEqualTo("Kimchi");
    }

    @Test
    public void addRestaurant() {
        Restaurant restaurant = new Restaurant("비룡", "서울");
        Restaurant saved = new Restaurant(1L, "비룡", "서울");

        given(restaurantRepository.save(any())).willReturn(saved);

        Restaurant newRestaurant = restaurantService.addRestaurant(restaurant);

        assertThat(newRestaurant.getId()).isEqualTo(1L);
    }

    @Test
    public void updateRestaurants() {
        Restaurant restaurant = new Restaurant(1L, "밥집", "서울");
        given(restaurantRepository.findById(1L)).willReturn(Optional.of(restaurant));

        restaurantService.updateRestaurants(1L, "술집", "부산");

        assertThat(restaurant.getName()).isEqualTo("술집");
        assertThat(restaurant.getLocation()).isEqualTo("부산");
    }
}
