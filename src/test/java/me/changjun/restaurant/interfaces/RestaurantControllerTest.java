package me.changjun.restaurant.interfaces;

import me.changjun.restaurant.application.RestaurantService;
import me.changjun.restaurant.domain.MenuItem;
import me.changjun.restaurant.domain.Restaurant;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest
public class RestaurantControllerTest {

    @Autowired
    private MockMvc mockMvc;

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
}
