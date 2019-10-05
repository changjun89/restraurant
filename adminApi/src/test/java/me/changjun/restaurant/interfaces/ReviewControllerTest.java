package me.changjun.restaurant.interfaces;

import me.changjun.restaurant.application.ReviewService;
import me.changjun.restaurant.domain.Review;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ReviewController.class)
public class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ReviewService reviewService;

    @Test
    public void reviewList() throws Exception {
        //given
        List<Review> mockReviews = new ArrayList<>();
        Review mockReview = Review.builder()
                .name("changjun")
                .score(3)
                .description("soso")
                .build();
        mockReviews.add(mockReview);

        //when
        given(reviewService.getReviews()).willReturn(mockReviews);

        //then
        mockMvc.perform(get("/api/reviews"))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsString("soso")));

    }
}
