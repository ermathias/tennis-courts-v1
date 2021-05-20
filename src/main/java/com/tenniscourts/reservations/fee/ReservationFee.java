package com.tenniscourts.reservations.fee;

import com.tenniscourts.reservations.Reservation;

import java.math.BigDecimal;

public interface ReservationFee {

    boolean isApplicable(Reservation reservation);

    default BigDecimal calculation(Reservation reservation) {
        return BigDecimal.ZERO;
    }

}
