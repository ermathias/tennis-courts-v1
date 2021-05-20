package com.tenniscourts.reservations.fee;

import com.tenniscourts.reservations.Reservation;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class FeeRuleGreaterThanTwelveHours implements ReservationFee {

    @Override
    public boolean isApplicable(Reservation reservation) {
        long hours = ChronoUnit.HOURS.between(LocalDateTime.now(), reservation.getSchedule().getStartDateTime());
        return hours >= 12 && hours < 24;
    }

    @Override
    public BigDecimal calculation(Reservation reservation) {
        return reservation.getValue().multiply(BigDecimal.valueOf(0.75));
    }
}
