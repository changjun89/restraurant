package me.changjun.restaurant.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id")
public class Restaurant {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String location;
    @Transient
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

    public void addMenuItem(MenuItem menuItem) {
        menuItems.add(menuItem);
    }

    public void setMenuItems(List<MenuItem> menuItems) {
        for (MenuItem menuItem : menuItems) {
            addMenuItem(menuItem);
        }
    }
}
