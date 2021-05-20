package com.tenniscourts.reservations;

import com.tenniscourts.config.api.RestAPI;
import com.tenniscourts.config.BaseRestController;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestAPI
@AllArgsConstructor
public class ReservationController extends BaseRestController implements ReservationAPIService {

    private final ReservationService reservationService;

    @Override
    public ResponseEntity<List<Long>> bookReservations(List<CreateReservationRequestDTO> reservationsRequestDTO) {
        List<Long> reservations = reservationService.bookReservations(reservationsRequestDTO).stream()
                .map(ReservationDTO::getId)
                .collect(Collectors.toList());
        return new ResponseEntity(reservations, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> bookReservation(CreateReservationRequestDTO createReservationRequestDTO) {
        return ResponseEntity.created(locationByEntity(reservationService.bookReservation(createReservationRequestDTO).getId())).build();
    }

    @Override
    public ResponseEntity<ReservationDTO> findReservation(Long reservationId) {
        return ResponseEntity.ok(reservationService.findReservation(reservationId));
    }

    @Override
    public ResponseEntity<ReservationDTO> cancelReservation(Long reservationId) {
        return ResponseEntity.ok(reservationService.cancelReservation(reservationId));
    }

    @Override
    public ResponseEntity<ReservationDTO> rescheduleReservation(Long reservationId, Long scheduleId) {
        return ResponseEntity.ok(reservationService.rescheduleReservation(reservationId, scheduleId));
    }
}
