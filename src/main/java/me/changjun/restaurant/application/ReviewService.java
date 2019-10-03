package me.changjun.restaurant.application;

import me.changjun.restaurant.domain.Review;
import me.changjun.restaurant.domain.ReviewRepository;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public Review addReview(Review review) {
        return reviewRepository.save(review);
    }
}
