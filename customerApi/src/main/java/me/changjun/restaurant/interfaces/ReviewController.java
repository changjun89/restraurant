package me.changjun.restaurant.interfaces;

import io.jsonwebtoken.Claims;
import me.changjun.restaurant.application.ReviewService;
import me.changjun.restaurant.domain.Review;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/api/restaurants/{restaurantId}/reviews")
    public ResponseEntity create(Authentication authentication,
                                 @PathVariable Long restaurantId,
                                 @Valid @RequestBody Review resource
    ) throws URISyntaxException {
        Claims claims = (Claims) authentication.getPrincipal();
        String name = claims.get("name", String.class);

        Integer score = resource.getScore();
        String description = resource.getDescription();

        Review createdReview = reviewService.addReview(restaurantId, name, score, description);
        URI uri = new URI("/api/restaurants/" + restaurantId + "/reviews/" + createdReview.getId());
        return ResponseEntity.created(uri).body("{}");
    }
}
