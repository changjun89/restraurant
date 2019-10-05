package me.changjun.restaurant.interfaces;

import me.changjun.restaurant.application.ReviewService;
import me.changjun.restaurant.domain.Review;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/api/reviews")
    public List<Review> list() {
        return reviewService.getReviews();
    }
}
