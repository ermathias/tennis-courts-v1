package com.tenniscourts.reservations.fee;

import com.tenniscourts.reservations.Reservation;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class FeeRuleGreaterThanTwoHours implements ReservationFee {

    @Override
    public boolean isApplicable(Reservation reservation) {
        long hours = ChronoUnit.HOURS.between(LocalDateTime.now(), reservation.getSchedule().getStartDateTime());
        return hours >= 2 && hours < 12;
    }

    @Override
    public BigDecimal calculation(Reservation reservation) {
        return reservation.getValue().multiply(BigDecimal.valueOf(0.5));
    }
}
