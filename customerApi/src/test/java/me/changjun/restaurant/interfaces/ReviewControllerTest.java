package me.changjun.restaurant.interfaces;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.changjun.restaurant.application.ReviewService;
import me.changjun.restaurant.domain.Review;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ReviewController.class)
public class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ReviewService reviewService;

    @Autowired
    private ObjectMapper objectMapper;



    @Test
    public void createWithValidData() throws Exception {
        Review review = Review.builder().id(1L)
                .name("changjun")
                .score(3)
                .description("good")
                .build();

        given(reviewService.addReview(eq(1L),any())).willReturn(review);
        mockMvc.perform(post("/api/restaurants/1/reviews")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"name\":\"changjun\",\"score\":3,\"description\":\"good\"}")
        )
                .andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.LOCATION,"/api/restaurants/1/reviews/1"));

        verify(reviewService).addReview(eq(1L),any());
    }


    @Test
    public void createWithInValidData() throws Exception {
        mockMvc.perform(post("/api/restaurants/1/reviews")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{}")
        )
                .andExpect(status().isBadRequest());

        verify(reviewService,never()).addReview(eq(1L),any());
    }
}
