package com.tenniscourts.reservations;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepositoryCustom {
    List<Reservation> getHistory(LocalDate startDate, LocalDate endDate);
}
