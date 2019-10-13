package me.changjun.restaurant.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    private Long restaurantId;
    @NotNull
    private Long userId;
    @NotEmpty
    private String name;
    @NotEmpty
    private LocalDate date;
    @NotEmpty
    private LocalTime time;
    @NotNull
    private int partySize;
}
