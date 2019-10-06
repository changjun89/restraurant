package me.changjun.restaurant.domain;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class CategoryTest {

    @Test
    public void creation() {
        Category category = Category.builder()
                .name("한식")
                .build();

        assertThat(category.getName()).isEqualTo("한식");
    }
}
