package me.changjun.restaurant.interfaces;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.changjun.restaurant.application.RestaurantService;
import me.changjun.restaurant.domain.MenuItem;
import me.changjun.restaurant.domain.Restaurant;
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
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest
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
        restaurants.add(new Restaurant(1L, "Bob zip", "seoul"));

        given(restaurantService.getRestaurants()).willReturn(restaurants);

        mockMvc.perform(get("/api/restaurants"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Bob zip"))
                .andExpect(jsonPath("$[0].location").value("seoul"));
    }

    @Test
    public void getRestaurant() throws Exception {

        Restaurant restaurant = new Restaurant(1L, "Bob zip", "seoul");
        restaurant.addMenuItem(new MenuItem("Kimchi"));

        Restaurant restaurant2 = new Restaurant(20L, "Cyber Food", "seoul");

        given(restaurantService.getRestaurantById(1L)).willReturn(restaurant);
        mockMvc.perform(get("/api/restaurants/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1L))
                .andExpect(jsonPath("name").value("Bob zip"))
                .andExpect(jsonPath("location").value("seoul"))
                .andExpect(jsonPath("menuItems[0].name").value("Kimchi"))
                .andExpect(content().string(Matchers.containsString("Kimchi")));

        given(restaurantService.getRestaurantById(20L)).willReturn(restaurant2);
        mockMvc.perform(get("/api/restaurants/20"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(20L))
                .andExpect(jsonPath("name").value("Cyber Food"))
                .andExpect(jsonPath("location").value("seoul"));
    }

    @Test
    public void create() throws Exception {
        Restaurant restaurant = new Restaurant(1L, "비룡", "부산");

        given(restaurantService.addRestaurant(any())).willReturn(restaurant);

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
    public void update() throws Exception {
        Restaurant restaurant = new Restaurant("비룡_수정", "부산");
        mockMvc.perform(patch("/api/restaurants/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(restaurant)))
                .andExpect(status().isOk());

        verify(restaurantService).updateRestaurants(1L, restaurant.getName(), restaurant.getLocation());

    }
}
