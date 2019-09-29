package me.changjun.restaurant.domain;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RestaurantTest {

    @Test
    public void creation() {
        Restaurant restaurant = Restaurant.builder().id(1L)
                .name("Bob zip")
                .location("seoul")
                .build();

        assertThat(restaurant.getId()).isEqualTo(1L);
        assertThat(restaurant.getName()).isEqualTo("Bob zip");
        assertThat(restaurant.getLocation()).isEqualTo("seoul");
    }
}
