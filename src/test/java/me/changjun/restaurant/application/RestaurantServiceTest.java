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
import java.util.Arrays;
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

    @Test
    public void getRestaurantById() {
        Restaurant restaurant = restaurantService.getRestaurantById(1L);
        assertThat(restaurant.getId()).isEqualTo(1L);

        String menuItemName = restaurant.getMenuItem().get(0).getName();
        assertThat(menuItemName).isEqualTo("Kimchi");
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
