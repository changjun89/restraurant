package me.changjun.restaurant.application;

import me.changjun.restaurant.domain.MenuItem;
import me.changjun.restaurant.domain.MenuItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
        menuItemService.bulkUpdate(1L, menuItems);

        verify(menuRepository, times(2)).save(any());
    }
}
