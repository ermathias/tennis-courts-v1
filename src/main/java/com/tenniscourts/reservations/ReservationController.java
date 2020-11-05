package com.tenniscourts.reservations;

import com.tenniscourts.config.BaseRestController;
import com.tenniscourts.guests.GuestDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping( "/reservations" )
public class ReservationController extends BaseRestController {

  @Autowired
  private final ReservationService reservationService;

  @PostMapping
  @ApiOperation( "Creates a new reservation to a tennis court" )
  @ApiResponses( value = { @ApiResponse( code = 201, message = "Reservation booked with success" ) } )
  public ResponseEntity< Void > bookReservation( @RequestBody CreateReservationRequestDTO createReservationRequestDTO ) {
    return ResponseEntity.created( locationByEntity( reservationService.bookReservation( createReservationRequestDTO ).getId() ) ).build();
  }

  @GetMapping( "/{id}" )
  @ApiOperation( "Find Reservation by id" )
  @ApiResponses( value = { @ApiResponse( code = 200, message = "Ok" ) } )
  public ResponseEntity< ReservationDTO > findReservation( Long reservationId ) {
    return ResponseEntity.ok( reservationService.findReservation( reservationId ) );
  }

  @PutMapping( "/{id}" )
  @ApiOperation( "Cancel Reservation by id" )
  @ApiResponses( value = { @ApiResponse( code = 200, message = "Ok" ) } )
  public ResponseEntity< ReservationDTO > cancelReservation( Long reservationId ) {
    return ResponseEntity.ok( reservationService.cancelReservation( reservationId ) );
  }

  @PutMapping
  @ApiOperation( "Reschedule Reservation by reservation id and schedule id" )
  @ApiResponses( value = { @ApiResponse( code = 200, message = "Ok" ) } )
  public ResponseEntity< ReservationDTO > rescheduleReservation( @RequestParam("reservationId") Long reservationId,
                                                                 @RequestParam("scheduleId") Long scheduleId ) {
    return ResponseEntity.ok( reservationService.rescheduleReservation( reservationId, scheduleId ) );
  }

  @GetMapping("/history")
  @ApiOperation( "Find all Reservations already made" )
  @ApiResponses( value = { @ApiResponse( code = 200, message = "Ok" ) } )
  public Iterable< ReservationDTO > findAll( Pageable pageable) {
    return reservationService.findAll(pageable);
  }
}
