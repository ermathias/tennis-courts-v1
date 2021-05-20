package com.tenniscourts.reservations.fee;

import com.tenniscourts.reservations.Reservation;

import java.math.BigDecimal;
import java.util.List;

public class CalculateReservationRefund {

    private final List<ReservationFee> fees;

    public CalculateReservationRefund(List<ReservationFee> fees) {
        this.fees = fees;
    }

    public BigDecimal verifyApplicabilityAndCalculate(Reservation reservation) {
        for (ReservationFee fee: this.fees) {
            if (fee.isApplicable(reservation)) {
                return fee.calculation(reservation);
            } else {
                continue;
            }
        }
        return BigDecimal.ZERO;
    }

}
