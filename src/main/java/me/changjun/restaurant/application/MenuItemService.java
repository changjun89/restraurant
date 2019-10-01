package me.changjun.restaurant.application;

import me.changjun.restaurant.domain.MenuItem;
import me.changjun.restaurant.domain.MenuItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuItemService {
    private final MenuItemRepository menuItemRepository;

    public MenuItemService(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    public void bulkUpdate(Long id, List<MenuItem> menuItems) {
        for (MenuItem menuItem : menuItems) {
            menuItem.setRestaurantId(id);
            menuItemRepository.save(menuItem);
        }
    }
}
