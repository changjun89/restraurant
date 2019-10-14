package me.changjun.restaurant.interfaces;

import me.changjun.restaurant.application.ReservationService;
import me.changjun.restaurant.domain.Reservation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ReservationController.class)
public class ReservationControllerTest {

    @MockBean
    ReservationService reservationService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getReservationWithRestaurantOwner() throws Exception {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjcsIm5hbWUiOiJDaGFuZ2p1biIsInJlc3RhdXJhbnRJZCI6MX0.NJEE-wffy1ImyPiN1VxoFt7-XeY8xKKIdxkQKx1FsC0";
        Long userId = 1L;
        String name = "Changjun";
        Long restaurantId = 1L;
        List<Reservation> reservations = new ArrayList<>();
        Reservation reservation = Reservation.builder()
                .restaurantId(1L)
                .id(1L)
                .userId(userId)
                .name(name)
                .build();
        reservations.add(reservation);

        given(reservationService.getReservation(restaurantId)).willReturn(reservations);

        mockMvc.perform(get("/api/reservations")
                .header("Authorization", "Bearer " + token)
        )
                .andExpect(status().isOk());
        verify(reservationService).getReservation(restaurantId);
    }

    @Test
    public void getReservationWithNoRestaurantOwner() throws Exception {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjEsIm5hbWUiOiJDaGFuZ2p1biJ9.9y_qfcOH9PlX6942ajVOpMs6ateGYL3OCuyUU9-uByw";

        mockMvc.perform(get("/api/reservations")
                .header("Authorization", "Bearer " + token)
        )
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
        verify(reservationService, times(0)).getReservation(any());
    }
}
