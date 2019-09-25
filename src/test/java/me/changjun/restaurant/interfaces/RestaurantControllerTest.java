package me.changjun.restaurant.interfaces;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
public class RestaurantControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void getRestaurants() throws Exception {

        mockMvc.perform(get("/api/restaurants"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("Bob zip"))
                .andExpect(jsonPath("location").value("seoul"));

    }
}
