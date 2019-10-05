package me.changjun.restaurant.domain;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RegionTest {

    @Test
    public void creation() {
        Region region = Region.builder()
                .name("서울")
                .build();

        assertThat(region.getName()).isEqualTo("서울");
    }
}
