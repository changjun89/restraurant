package me.changjun.restaurant.application;

import me.changjun.restaurant.domain.MenuItem;
import me.changjun.restaurant.domain.MenuItemRepository;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MenuItemServiceTest {

    private MenuItemService menuItemService;
    @Mock
    private MenuItemRepository menuRepository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        menuItemService = new MenuItemService(menuRepository);
    }

    @Test
    public void bulkUpdate() {
        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(MenuItem.builder()
                .name("kimchi")
                .build());
        menuItems.add(MenuItem.builder()
                .name("gukbob")
                .build());

        menuItems.add(MenuItem.builder()
                .id(1004L)
                .destroy(true)
                .build());
        menuItemService.bulkUpdate(1L, menuItems);

        verify(menuRepository, times(2)).save(any());
        verify(menuRepository, times(1)).deleteById(eq(1004L));
    }

    @Test
    public void getMenuList() {

        List<MenuItem> mockMenuItems = new ArrayList<>() ;
        MenuItem mockMenuItem = MenuItem.builder()
                .name("kimchi")
                .restaurantId(1L)
                .build();
        mockMenuItems.add(mockMenuItem);

        given(menuRepository.findAllByRestaurantId(1L)).willReturn(mockMenuItems);

        List<MenuItem> menuItems = menuItemService.getMenuItemsByRestaurantId(1L);
        MenuItem menuItem = menuItems.get(0);
        assertThat(menuItem.getName()).isEqualTo("kimchi");

    }


}
