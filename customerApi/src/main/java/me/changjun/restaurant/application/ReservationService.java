package me.changjun.restaurant.application;

import me.changjun.restaurant.domain.Reservation;
import me.changjun.restaurant.domain.ReservationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
@Transactional
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Reservation addReservation(Long restaurantId, Long userId, String name, LocalDate date, LocalTime time, int partySize) {
        Reservation reservation = Reservation.builder()
                .restaurantId(restaurantId)
                .userId(userId)
                .name(name)
                .date(date)
                .time(time)
                .partySize(partySize)
                .build();
        return reservationRepository.save(reservation);
    }
}
