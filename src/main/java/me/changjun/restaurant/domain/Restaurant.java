package me.changjun.restaurant.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Restaurant {

    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    @NotEmpty
    private String name;
    @NotEmpty
    private String location;
    @Transient
    private List<MenuItem> menuItem;

    public void addMenuItem(MenuItem menuItem) {
        this.menuItem.add(menuItem);
    }

    public void setMenuItems(List<MenuItem> menuItems) {
        this.menuItem = new ArrayList<>();
        for (MenuItem menuItem : menuItems) {
            addMenuItem(menuItem);
        }
    }

    public Restaurant updateInfo(String name, String location) {
        this.name = name;
        this.location = location;
        return this;
    }
}
