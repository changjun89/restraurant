package me.changjun.restaurant.application;

import me.changjun.restaurant.domain.Review;
import me.changjun.restaurant.domain.ReviewRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

public class ReviewServiceTest {

    private ReviewService reviewService;

    @Mock
    private ReviewRepository reviewRepository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        reviewService = new ReviewService(reviewRepository);
    }
    @Test
    public void addReview() {
        Review review = Review.builder()
                .name("chanjun")
                .score(3)
                .description("good").build();
        reviewService.addReview(1L,review);
        
        verify(reviewRepository).save(any());
    }
}
