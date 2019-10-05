package me.changjun.restaurant.application;

import me.changjun.restaurant.domain.Review;
import me.changjun.restaurant.domain.ReviewRepository;
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
    public void list() {
        List<Review> mockReviews = new ArrayList<>();

        Review mockReview = Review.builder()
                .name("changjun")
                .score(3)
                .description("soso")
                .build();

        mockReviews.add(mockReview);

        given(reviewRepository.findAll()).willReturn(mockReviews);

        List<Review> reviews = reviewService.getReviews();
        Review review = reviews.get(0);

        assertThat(review.getDescription()).isEqualTo("soso");
        assertThat(review.getName()).isEqualTo("changjun");
        assertThat(review.getScore()).isEqualTo(3);
    }
}
