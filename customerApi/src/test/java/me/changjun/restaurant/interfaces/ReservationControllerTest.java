package me.changjun.restaurant.interfaces;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.changjun.restaurant.application.ReservationService;
import me.changjun.restaurant.domain.Reservation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ReservationController.class)
public class ReservationControllerTest {

    @MockBean
    ReservationService reservationService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void create() throws Exception {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjEsIm5hbWUiOiJDaGFuZ2p1biJ9.9y_qfcOH9PlX6942ajVOpMs6ateGYL3OCuyUU9-uByw";
        Long userId = 1L;
        String name = "Changjun";
        LocalDate date = LocalDate.of(2019, 10, 20);
        LocalTime time = LocalTime.of(12, 00);
        int partySize = 5;
        Long restaurantId = 1L;

        Reservation reservation = Reservation.builder()
                .id(1L)
                .userId(userId)
                .name(name)
                .date(date)
                .time(time)
                .partySize(partySize)
                .build();

        given(reservationService.addReservation(restaurantId, userId, name, date, time, partySize)).willReturn(reservation);

        mockMvc.perform(post("/api/restaurant/1/reservations")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(reservation))
                .header("Authorization", "Bearer " + token)
        )
                .andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.LOCATION, "/api/restaurant/1/reservations/1"));

        verify(reservationService).addReservation(eq(restaurantId), eq(userId), eq(name), eq(date), eq(time), eq(partySize));
    }
}
