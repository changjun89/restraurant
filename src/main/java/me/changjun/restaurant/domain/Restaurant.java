package me.changjun.restaurant.domain;

import lombok.*;

import javax.persistence.Entity;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id")
public class Restaurant {

    private Long id;
    private String name;
    private String location;

    public Restaurant(long id, String name, String location) {
        this.id = id;
        this.name = name;
        this.location = location;
    }
}
