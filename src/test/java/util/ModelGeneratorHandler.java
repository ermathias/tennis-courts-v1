package util;

import com.tenniscourts.reservations.Reservation;

public class ModelGeneratorHandler {

    public static Reservation setReservationId(Reservation reservation) {
        reservation.setId(1L);
        return reservation;
    }
}
