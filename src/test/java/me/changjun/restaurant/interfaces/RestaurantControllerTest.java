package me.changjun.restaurant.interfaces;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.changjun.restaurant.application.RestaurantService;
import me.changjun.restaurant.domain.MenuItem;
import me.changjun.restaurant.domain.Restaurant;
import me.changjun.restaurant.domain.RestaurantNotFoundException;
import me.changjun.restaurant.domain.Review;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(RestaurantController.class)
public class RestaurantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RestaurantService restaurantService;

    @Test
    public void getRestaurants() throws Exception {
        List<Restaurant> restaurants = new ArrayList<>();
        Restaurant restaurant = Restaurant.builder().id(1L).name("Bob zip").location("seoul").build();
        restaurants.add(restaurant);

        given(restaurantService.getRestaurants()).willReturn(restaurants);

        mockMvc.perform(get("/api/restaurants"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Bob zip"))
                .andExpect(jsonPath("$[0].location").value("seoul"));
    }

    @Test
    public void getRestaurant() throws Exception {

        Restaurant restaurant = Restaurant.builder().id(1L).name("Bob zip").location("seoul").build();
        restaurant.setMenuItems(Arrays.asList(new MenuItem("Kimchi")));

        Review review = Review.builder().name("changjun").score(3).description("good").build();
        List<Review> reviews = Arrays.asList(review);
        restaurant.setReviews(reviews);

        Restaurant restaurant2 = Restaurant.builder().id(20L).name("Cyber Food").location("seoul").build();


        given(restaurantService.getRestaurantById(1L)).willReturn(restaurant);
        mockMvc.perform(get("/api/restaurants/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1L))
                .andExpect(jsonPath("name").value("Bob zip"))
                .andExpect(jsonPath("location").value("seoul"))
                .andExpect(jsonPath("menuItem[0].name").value("Kimchi"))
                .andExpect(content().string(Matchers.containsString("Kimchi")))
                .andExpect(content().string(Matchers.containsString("good")));

        given(restaurantService.getRestaurantById(20L)).willReturn(restaurant2);
        mockMvc.perform(get("/api/restaurants/20"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(20L))
                .andExpect(jsonPath("name").value("Cyber Food"))
                .andExpect(jsonPath("location").value("seoul"));
    }

    @Test
    public void getRestaurantWithNotExisted() throws Exception {
        given(restaurantService.getRestaurantById(404L)).willThrow(new RestaurantNotFoundException(404L));
        mockMvc.perform(get("/api/restaurants/404"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("{}"));
    }

    @Test
    public void createWithValidDate() throws Exception {
        Restaurant restaurant = Restaurant.builder().id(1L).name("밥집").location("서울").build();

        given(restaurantService.addRestaurant(restaurant)).willReturn(restaurant);

        mockMvc.perform(post("/api/restaurants")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(restaurant))
        )
                .andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.LOCATION, "/api/restaurants/1"))
                .andExpect(content().string("{}"))
                .andDo(print());

        verify(restaurantService).addRestaurant(any());
    }

    @Test
    public void createWithInValidDate() throws Exception {

        Restaurant restaurant = Restaurant.builder().build();
        given(restaurantService.addRestaurant(restaurant)).willReturn(restaurant);

        mockMvc.perform(post("/api/restaurants")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(restaurant))
        )
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    public void updateWithValidRequest() throws Exception {
        Restaurant restaurant = Restaurant.builder().name("비룡_수정").location("부산").build();
        mockMvc.perform(patch("/api/restaurants/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(restaurant)))
                .andExpect(status().isOk());

        verify(restaurantService).updateRestaurants(1L, restaurant.getName(), restaurant.getLocation());
    }


    @Test
    public void updateWithInValidRequest() throws Exception {
        Restaurant restaurant = Restaurant.builder().build();
        mockMvc.perform(patch("/api/restaurants/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(restaurant)))
                .andExpect(status().isBadRequest());
    }
}
