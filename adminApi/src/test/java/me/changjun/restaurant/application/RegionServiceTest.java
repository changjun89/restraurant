package me.changjun.restaurant.application;

import me.changjun.restaurant.domain.Region;
import me.changjun.restaurant.domain.RegionRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

public class RegionServiceTest {

    private RegionService regionService;
    @Mock
    private RegionRepository regionRepository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        regionService = new RegionService(regionRepository);
    }

    @Test
    public void list() {
        //given
        List<Region> mockRegions = new ArrayList<>();
        Region mockRegion = Region.builder()
                .name("서울")
                .build();
        mockRegions.add(mockRegion);

        //when
        given(regionRepository.findAll()).willReturn(mockRegions);

        //then
        List<Region> regions = regionService.getRegions();
        Region region = regions.get(0);

        assertThat(region.getName()).isEqualTo("서울");
    }

    @Test
    public void addRegion() {
        //given
        Region region = Region.builder()
                .name("서울")
                .build();
        Region newRegion = Region.builder()
                .id(1L)
                .name("서울")
                .build();
        given(regionRepository.save(region)).willReturn(newRegion);

        //when
        Region createdRegion = regionService.addRegion(region);

        //then
        verify(regionRepository).save(any());
        assertThat(createdRegion.getName()).isEqualTo("서울");
        assertThat(createdRegion.getId()).isEqualTo(1L);

    }
}
