package com.tenniscourts.reservations;

import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.guests.Guest;
import com.tenniscourts.guests.GuestDTO;
import com.tenniscourts.guests.GuestRepository;
import com.tenniscourts.schedules.Schedule;
import com.tenniscourts.schedules.ScheduleRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@AllArgsConstructor
public class ReservationService {

  @Autowired
  private final ReservationRepository reservationRepository;

  @Autowired
  private final ScheduleRepository scheduleRepository;

  @Autowired
  private final GuestRepository guestRepository;

  @Autowired
  private final ReservationMapper reservationMapper;

  private Guest validateGuest( Long id ) {
    return guestRepository.findById( id ).orElseThrow(()->new EntityNotFoundException( "Guest not exists" ));
  }

  private Schedule validateSchedule( Long id ) {
    return scheduleRepository.findById( id ).orElseThrow(()->new EntityNotFoundException( "Schedule not exists" ));
  }

  @Transactional( rollbackFor = Exception.class )
  public ReservationDTO bookReservation( CreateReservationRequestDTO createReservationRequestDTO ) {

    var guest = validateGuest( createReservationRequestDTO.getGuestId() );
    var schedule = validateSchedule( createReservationRequestDTO.getScheduleId() );

    Reservation newReservation = Reservation.builder()
            .guest( guest )
            .schedule( schedule )
            .reservationStatus( ReservationStatus.READY_TO_PLAY )
            .refundValue( new BigDecimal( 10 ) )
            .value( new BigDecimal( 10 ) )
            .build();
    return reservationMapper.map( reservationRepository.save( newReservation ) );
  }

  public List< ReservationDTO > findAll( Pageable pageable ) {
    return reservationMapper.map( reservationRepository.findAll(pageable).getContent() );
  }

  public ReservationDTO findReservation( Long reservationId ) {
    return reservationRepository.findById( reservationId ).map( reservationMapper::map ).orElseThrow( () -> {
      throw new EntityNotFoundException( "Reservation not found." );
    } );
  }

  public ReservationDTO cancelReservation( Long reservationId ) {
    return reservationMapper.map( this.cancel( reservationId ) );
  }

  private Reservation cancel( Long reservationId ) {
    return reservationRepository.findById( reservationId ).map( reservation -> {

      this.validateCancellation( reservation );

      BigDecimal refundValue = getRefundValue( reservation );
      return this.updateReservation( reservation, refundValue, ReservationStatus.CANCELLED );

    } ).orElseThrow( () -> {
      throw new EntityNotFoundException( "Reservation not found." );
    } );
  }

  private Reservation updateReservation( Reservation reservation, BigDecimal refundValue, ReservationStatus status ) {
    reservation.setReservationStatus( status );
    reservation.setValue( reservation.getValue().subtract( refundValue ) );
    reservation.setRefundValue( refundValue );

    return reservationRepository.save( reservation );
  }

  private void validateCancellation( Reservation reservation ) {
    if ( !ReservationStatus.READY_TO_PLAY.equals( reservation.getReservationStatus() ) ) {
      throw new IllegalArgumentException( "Cannot cancel/reschedule because it's not in ready to play status." );
    }

    if ( reservation.getSchedule().getStartDateTime().isBefore( LocalDateTime.now() ) ) {
      throw new IllegalArgumentException( "Can cancel/reschedule only future dates." );
    }
  }

  public BigDecimal getRefundValue( Reservation reservation ) {
    long hours = ChronoUnit.HOURS.between( LocalDateTime.now(), reservation.getSchedule().getStartDateTime() );

    if ( hours >= 24 ) {
      return reservation.getValue();
    }

    return BigDecimal.ZERO;
  }

  @Transactional( rollbackFor = Exception.class )
  public ReservationDTO rescheduleReservation( Long previousReservationId, Long scheduleId ) {
    Reservation previousReservation = cancel( previousReservationId );

    if ( scheduleId.equals( previousReservation.getSchedule().getId() ) ) {
      throw new IllegalArgumentException( "Cannot reschedule to the same slot." );
    }

    previousReservation.setReservationStatus( ReservationStatus.RESCHEDULED );
    reservationRepository.save( previousReservation );

    ReservationDTO newReservation = bookReservation( CreateReservationRequestDTO.builder()
            .guestId( previousReservation.getGuest().getId() )
            .scheduleId( scheduleId )
            .build() );
    newReservation.setPreviousReservation( reservationMapper.map( previousReservation ) );
    return newReservation;
  }
}
