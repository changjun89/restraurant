package me.changjun.restaurant.application;

import me.changjun.restaurant.domain.Reservation;
import me.changjun.restaurant.domain.ReservationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<Reservation> getReservation(Long restaurantId) {
        return reservationRepository.findByRestaurantId(restaurantId);
    }
}
