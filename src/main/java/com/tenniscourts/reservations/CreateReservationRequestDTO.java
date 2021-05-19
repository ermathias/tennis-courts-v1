package com.tenniscourts.reservations;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotNull;

import com.tenniscourts.schedules.Schedule;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Data
public class CreateReservationRequestDTO{

    @NotNull
    private Long guestId;

//    @NotNull
    private Long scheduleId;
    
//    @NotNull
//    private String courtName;
    
//    @NotNull
//    private LocalDateTime bookingSlot;

}
