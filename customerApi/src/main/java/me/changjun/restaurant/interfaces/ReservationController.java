package me.changjun.restaurant.interfaces;

import io.jsonwebtoken.Claims;
import me.changjun.restaurant.application.ReservationService;
import me.changjun.restaurant.domain.Reservation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalTime;

@RestController
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/api/restaurant/{restaurantId}/reservations")
    public ResponseEntity create(@PathVariable(name = "restaurantId") Long restaurantId,
                                 Authentication authentication,
                                 @RequestBody @Valid Reservation resource
    ) throws URISyntaxException {
        Claims claims = (Claims) authentication.getPrincipal();
        Long userId = claims.get("userId", Long.class);
        String name = claims.get("name", String.class);
        LocalDate date = resource.getDate();
        LocalTime time = resource.getTime();
        int partySize = resource.getPartySize();

        Reservation reservation = reservationService.addReservation(restaurantId, userId, name, date, time, partySize);

        URI uri = new URI("/api/restaurant/" + restaurantId + "/reservations/" + reservation.getId());
        return ResponseEntity.created(uri).body("{}");
    }
}
