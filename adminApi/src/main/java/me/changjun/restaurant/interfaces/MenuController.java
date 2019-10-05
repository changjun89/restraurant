package me.changjun.restaurant.interfaces;

import me.changjun.restaurant.application.MenuItemService;
import me.changjun.restaurant.domain.MenuItem;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MenuController {

    private final MenuItemService menuItemService;

    public MenuController(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }

    @GetMapping("/api/restaurants/{restaurantsId}/menuItems")
    public List<MenuItem> getMenuItems(
            @PathVariable(name = "restaurantsId") Long id) {
        return menuItemService.getMenuItemsByRestaurantId(id);
    }

    @PatchMapping("/api/restaurants/{restaurantsId}/menuItems")
    public String bulkUpdate(
            @PathVariable(name = "restaurantsId") Long id,
            @RequestBody List<MenuItem> menuItems) {
        menuItemService.bulkUpdate(id, menuItems);
        return "";
    }

}

