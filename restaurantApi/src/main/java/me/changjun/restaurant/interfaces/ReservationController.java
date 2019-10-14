package me.changjun.restaurant.interfaces;

import io.jsonwebtoken.Claims;
import me.changjun.restaurant.application.ReservationService;
import me.changjun.restaurant.domain.Reservation;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/api/reservations")
    public List<Reservation> create(Authentication authentication) {
        Claims claims = (Claims) authentication.getPrincipal();
        Long restaurantId = claims.get("restaurantId", Long.class);
        if (restaurantId == null) {
            return new ArrayList<>();
        }
        return reservationService.getReservation(restaurantId);
    }
}
