package me.changjun.restaurant.domain;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id")
public class Restaurant {

    private Long id;
    private String name;
    private String location;
    private List<MenuItem> menuItems = new ArrayList<>();

    public Restaurant(String name, String location) {
        this.name = name;
        this.location = location;
    }

    public Restaurant(long id, String name, String location) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.menuItems = menuItems;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void addMenuItem(MenuItem menuItem) {
        menuItems.add(menuItem);
    }

    public void setMenuItems(List<MenuItem> menuItems) {
        for (MenuItem menuItem : menuItems) {
            addMenuItem(menuItem);
        }
    }
}
