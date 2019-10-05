package me.changjun.restaurant.interfaces;

import me.changjun.restaurant.application.MenuItemService;
import me.changjun.restaurant.domain.MenuItem;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(MenuController.class)
public class MenuControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MenuItemService menuItemService;

    @Test
    public void bulkUpdate() throws Exception {
        mockMvc.perform(patch("/api/restaurants/1/menuItems")
                .content("[]")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isOk());
        verify(menuItemService).bulkUpdate(eq(1L), any());
    }

    @Test
    public void getMenuList() throws Exception {
        List<MenuItem> menuItems = new ArrayList<>();
        MenuItem menuItem = MenuItem.builder()
                .name("kimchi")
                .restaurantId(1L)
                .build();

        MenuItem menuItem1 = MenuItem.builder()
                .name("bob")
                .restaurantId(1L)
                .build();

        menuItems.add(menuItem);
        menuItems.add(menuItem1);

        given(menuItemService.getMenuItemsByRestaurantId(1L)).willReturn(menuItems);
        mockMvc.perform(get("/api/restaurants/1/menuItems"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsString("kimchi")))
                .andExpect(content().string(Matchers.containsString("bob")));
    }
}
