package com.tenniscourts.reservations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tenniscourts.config.BaseRestController;
import com.tenniscourts.schedules.Schedule;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.SimpleDateFormat;


@AllArgsConstructor
@Controller
public class ReservationController extends BaseRestController {

    private  ReservationService reservationService;

    @Autowired
    private ReservationRepository resRepo;

    @GetMapping("/reservations/{id}")
    @ResponseBody
    public String getReservation(@PathVariable(value = "id") Long id) throws IOException {
        Reservation res = reservationService.findById(id);
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(res);

        return jsonInString;
    }

    @PostMapping(value= "/addReservation")
    @ResponseBody
    @Transactional
    public String addReservation(@RequestBody String reservationJSon) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss.s");
        mapper.setDateFormat(format);
        Reservation res = mapper.readValue(reservationJSon, Reservation.class);
        resRepo.save(res);
        return "redirect:/";
    }

   // public ResponseEntity<Void> bookReservation(CreateReservationRequestDTO createReservationRequestDTO) {
       // return ResponseEntity.created(locationByEntity(reservationService.bookReservation(createReservationRequestDTO).getId())).build();

    public ResponseEntity<ReservationDTO> findReservation(Long reservationId) {
        return ResponseEntity.ok(reservationService.findReservation(reservationId));
    }

    public ResponseEntity<ReservationDTO> cancelReservation(Long reservationId) {
        return ResponseEntity.ok(reservationService.cancelReservation(reservationId));
    }

    public ResponseEntity<ReservationDTO> rescheduleReservation(Long reservationId, Long scheduleId) {
        return ResponseEntity.ok(reservationService.rescheduleReservation(reservationId, scheduleId));
    }
}
