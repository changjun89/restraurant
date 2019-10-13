package me.changjun.restaurant.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
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
    private Long categoryId;
    @NotEmpty
    private String name;
    @NotEmpty
    private String location;
    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<MenuItem> menuItem;
    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Review> setReviews;

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

    public void setReviews(List<Review> reviews) {
        this.setReviews = new ArrayList<>(reviews);
    }
}
