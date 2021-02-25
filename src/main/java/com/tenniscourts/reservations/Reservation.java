package com.tenniscourts.reservations;

import com.tenniscourts.config.persistence.BaseEntity;
import com.tenniscourts.guests.Guest;
import com.tenniscourts.schedules.Schedule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
    private static final long serialVersionUID = 1L;
    
    @NotNull
    @ManyToOne
    private Guest guest;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_schedule")
    private Schedule schedule;

    @NotNull
    @Builder.Default
    private BigDecimal value = BigDecimal.ZERO;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private ReservationStatus reservationStatus = ReservationStatus.READY_TO_PLAY;

    @Builder.Default
    private BigDecimal refundValue = BigDecimal.ZERO;
}
