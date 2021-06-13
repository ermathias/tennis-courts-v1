package com.tenniscourts.entity;

import com.tenniscourts.config.persistence.BaseEntity;
import com.tenniscourts.util.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
    @NotNull
    private Guest guest;

    @ManyToOne
    @NotNull
    private Schedule schedule;

    @NotNull
    private BigDecimal value;

    @Enumerated(EnumType.STRING)
    @NotNull
    private ReservationStatus reservationStatus = ReservationStatus.READY_TO_PLAY;

    private BigDecimal refundValue;
}
