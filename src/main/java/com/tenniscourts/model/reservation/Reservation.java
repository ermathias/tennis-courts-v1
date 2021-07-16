package com.tenniscourts.model.reservation;

import com.tenniscourts.config.persistence.BaseEntity;
import com.tenniscourts.dto.ReservationDTO;
import com.tenniscourts.model.guests.Guest;
import com.tenniscourts.model.schedule.Schedule;
import com.tenniscourts.bean.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;


@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
public class Reservation extends BaseEntity<Long> {

    @OneToOne
    private Guest guest;

    @ManyToOne
    @NotNull
    private Schedule schedule;

    @NotNull
    private BigDecimal value;

    @NotNull
    private ReservationStatus reservationStatus = ReservationStatus.READY_TO_PLAY;

    private BigDecimal refundValue;

    public static ReservationDTO toDto(Reservation reservation) {
        return ReservationDTO.builder()
                .id(reservation.getId())
                .guestId(reservation.getGuest().getId())
                .schedule(Schedule.toDto(reservation.getSchedule()))
                .refundValue(reservation.getRefundValue())
                .value(reservation.getValue())
                .scheduledId(reservation.getSchedule().getId())
                .build();
    }
}
