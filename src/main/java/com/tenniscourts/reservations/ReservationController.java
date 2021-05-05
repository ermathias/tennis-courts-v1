package com.tenniscourts.reservations;

import com.tenniscourts.config.BaseRestController;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/reservation")
public class ReservationController extends BaseRestController {

    private final ReservationService reservationService;

    @PostMapping("/bookReservation")
    public ResponseEntity<Void> bookReservation(CreateReservationRequestDTO createReservationRequestDTO) {
        return ResponseEntity.created(locationByEntity(reservationService.bookReservation(createReservationRequestDTO).getId())).build();
    }

    @GetMapping("/findReservation/{reservationId}")
    public ResponseEntity<ReservationDTO> findReservation(Long reservationId) {
        return ResponseEntity.ok(reservationService.findReservation(reservationId));
    }

    @PostMapping("/{reservationId}")
    public ResponseEntity<ReservationDTO> cancelReservation(Long reservationId) {
        return ResponseEntity.ok(reservationService.cancelReservation(reservationId));
    }

    @PostMapping("/{reservationId}/{newScheduledId}")
    public ResponseEntity<Object> cancelOrRescheduleReservation(@PathVariable("reservationId") Long reservationId, @PathVariable(required = false, name = "newScheduledId") Long newScheduledId) {
        try{
        if(newScheduledId != null) {
            return ResponseEntity.ok(reservationService.rescheduleReservation(reservationId, newScheduledId));
        }else{
            return ResponseEntity.ok(reservationService.cancelReservation(reservationId));
        }
        }catch (IllegalArgumentException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }

    }
    
    @PostMapping("/rescheduleReservation/{reservationId}/{newScheduledId}")
    public ResponseEntity<ReservationDTO> rescheduleReservation(Long reservationId, Long scheduleId) {
        return ResponseEntity.ok(reservationService.rescheduleReservation(reservationId, scheduleId));
    }
}
