package me.changjun.restaurant.application;

import me.changjun.restaurant.domain.Reservation;
import me.changjun.restaurant.domain.ReservationRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

public class ReservationServiceTest {

    ReservationService reservationService;
    @Mock
    private ReservationRepository reservationRepository;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        reservationService = new ReservationService(reservationRepository);
    }

    @Test
    public void addReservation() {
        Long restaurantId = 1L;
        Long userId = 1L;
        String name = "Changjun";
        LocalDate date = LocalDate.of(2019, 10, 20);
        LocalTime time = LocalTime.of(12, 00);
        int partySize = 5;

        Reservation mockReservation = Reservation.builder()
                .id(1L)
                .userId(userId)
                .name(name)
                .date(date)
                .time(time)
                .partySize(partySize)
                .build();
        given(reservationRepository.save(any())).willReturn(mockReservation);

        Reservation reservation = reservationService.addReservation(restaurantId, userId, name, date, time, partySize);

        verify(reservationRepository).save(any());
        assertThat(reservation.getId()).isNotNull();
        assertThat(reservation.getUserId()).isEqualTo(userId);
        assertThat(reservation.getName()).isEqualTo(name);
        assertThat(reservation.getDate()).isEqualTo(date);
        assertThat(reservation.getTime()).isEqualTo(time);
        assertThat(reservation.getPartySize()).isEqualTo(partySize);
    }
}
