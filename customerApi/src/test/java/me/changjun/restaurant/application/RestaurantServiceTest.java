package me.changjun.restaurant.application;

import me.changjun.restaurant.domain.*;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

public class RestaurantServiceTest {

    private RestaurantService restaurantService;

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private MenuItemRepository menuItemRepository;

    @Mock
    private ReviewRepository reviewRepository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        mockRestaurantRepository();
        mockMenuItemRepository();
        mockMenuReviewRepository();

        restaurantService = new RestaurantService(restaurantRepository, menuItemRepository, reviewRepository);
    }

    private void mockMenuReviewRepository() {
        List<Review> reviews = new ArrayList<>();
        reviews.add(Review.builder().name("changjun").score(2).description("soso").build());
        given(reviewRepository.findAllByRestaurantId(1L)).willReturn(reviews);
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
                .location("서울").build();
        restaurant.setMenuItem(Arrays.asList(new MenuItem("Kimchi")));
        restaurants.add(restaurant);
        given(restaurantRepository.findAllByLocationContaining("서울")).willReturn(restaurants);
        given(restaurantRepository.findById(1L)).willReturn(Optional.of(restaurant));

    }

    @Test
    public void getRestaurants() {
        List<Restaurant> restaurants = restaurantService.getRestaurants("서울");
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

        String menuItemName = restaurant.getMenuItem().get(0).getName();
        assertThat(menuItemName).isEqualTo("Kimchi");

        String description = restaurant.getSetReviews().get(0).getDescription();
        assertThat(description).isEqualTo("soso");

        verify(menuItemRepository).findAllByRestaurantId(eq(1L));
        verify(reviewRepository).findAllByRestaurantId(eq(1L));
    }
}
