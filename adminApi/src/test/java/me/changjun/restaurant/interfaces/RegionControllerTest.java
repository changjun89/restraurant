package me.changjun.restaurant.interfaces;

import me.changjun.restaurant.application.RegionService;
import me.changjun.restaurant.domain.Region;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(RegionController.class)
public class RegionControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RegionService regionService;

    @Test
    public void list() throws Exception {
        //given
        List<Region> mockRegions = new ArrayList<>();
        Region mockRegion = Region.builder()
                .name("서울")
                .build();
        mockRegions.add(mockRegion);

        //when
        given(regionService.getRegions()).willReturn(mockRegions);

        //then
        mockMvc.perform(get("/api/regions"))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsString("서울")));
    }


    @Test
    public void create() throws Exception {
        Region region = Region.builder()
                .id(1L)
                .name("서울")
                .build();

        given(regionService.addRegion(any())).willReturn(region);

        mockMvc.perform(post("/api/regions")
                .content("{\"name\":\"서울\"}")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.LOCATION,"/api/regions/"+region.getId()))
                .andExpect(content().string("{}"));

        verify(regionService).addRegion(any());
    }
}
