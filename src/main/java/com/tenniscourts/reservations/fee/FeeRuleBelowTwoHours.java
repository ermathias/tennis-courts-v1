package com.tenniscourts.reservations.fee;

import com.tenniscourts.reservations.Reservation;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class FeeRuleBelowTwoHours implements ReservationFee {

    private long twoHourInMinutes = 160;

    @Override
    public boolean isApplicable(Reservation reservation) {
        long minutes = ChronoUnit.MINUTES.between(LocalDateTime.now(), reservation.getSchedule().getStartDateTime());
        return minutes < twoHourInMinutes;
    }

    @Override
    public BigDecimal calculation(Reservation reservation) {
        return reservation.getValue().multiply(BigDecimal.valueOf(0.25));
    }
}
