package com.tenniscourts.reservations;

import com.tenniscourts.guests.Guest;
import com.tenniscourts.schedules.Schedule;
import com.tenniscourts.schedules.ScheduleDTO;
import com.tenniscourts.tenniscourts.TennisCourt;
import com.tenniscourts.tenniscourts.TennisCourtDTO;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-05-19T19:59:16+0530",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_191 (Oracle Corporation)"
)
@Component
public class ReservationMapperImpl implements ReservationMapper {

    @Override
    public Reservation map(ReservationDTO source) {
        if ( source == null ) {
            return null;
        }

        Reservation reservation = new Reservation();

        reservation.setId( source.getId() );
        reservation.setSchedule( scheduleDTOToSchedule( source.getSchedule() ) );
        reservation.setValue( source.getValue() );
        if ( source.getReservationStatus() != null ) {
            reservation.setReservationStatus( Enum.valueOf( ReservationStatus.class, source.getReservationStatus() ) );
        }
        reservation.setRefundValue( source.getRefundValue() );

        return reservation;
    }

    @Override
    public ReservationDTO map(Reservation source) {
        if ( source == null ) {
            return null;
        }

        ReservationDTO reservationDTO = new ReservationDTO();

        reservationDTO.setId( source.getId() );
        reservationDTO.setSchedule( scheduleToScheduleDTO( source.getSchedule() ) );
        if ( source.getReservationStatus() != null ) {
            reservationDTO.setReservationStatus( source.getReservationStatus().name() );
        }
        reservationDTO.setRefundValue( source.getRefundValue() );
        reservationDTO.setValue( source.getValue() );

        return reservationDTO;
    }

    @Override
    public Reservation map(CreateReservationRequestDTO source) {
        if ( source == null ) {
            return null;
        }

        Reservation reservation = new Reservation();

        reservation.setGuest( createReservationRequestDTOToGuest( source ) );
        reservation.setSchedule( createReservationRequestDTOToSchedule( source ) );

        return reservation;
    }

    protected TennisCourt tennisCourtDTOToTennisCourt(TennisCourtDTO tennisCourtDTO) {
        if ( tennisCourtDTO == null ) {
            return null;
        }

        TennisCourt tennisCourt = new TennisCourt();

        tennisCourt.setId( tennisCourtDTO.getId() );
        tennisCourt.setName( tennisCourtDTO.getName() );

        return tennisCourt;
    }

    protected Schedule scheduleDTOToSchedule(ScheduleDTO scheduleDTO) {
        if ( scheduleDTO == null ) {
            return null;
        }

        Schedule schedule = new Schedule();

        schedule.setId( scheduleDTO.getId() );
        schedule.setTennisCourt( tennisCourtDTOToTennisCourt( scheduleDTO.getTennisCourt() ) );
        schedule.setStartDateTime( scheduleDTO.getStartDateTime() );
        schedule.setEndDateTime( scheduleDTO.getEndDateTime() );

        return schedule;
    }

    protected TennisCourtDTO tennisCourtToTennisCourtDTO(TennisCourt tennisCourt) {
        if ( tennisCourt == null ) {
            return null;
        }

        TennisCourtDTO tennisCourtDTO = new TennisCourtDTO();

        tennisCourtDTO.setId( tennisCourt.getId() );
        tennisCourtDTO.setName( tennisCourt.getName() );

        return tennisCourtDTO;
    }

    protected ScheduleDTO scheduleToScheduleDTO(Schedule schedule) {
        if ( schedule == null ) {
            return null;
        }

        ScheduleDTO scheduleDTO = new ScheduleDTO();

        scheduleDTO.setId( schedule.getId() );
        scheduleDTO.setTennisCourt( tennisCourtToTennisCourtDTO( schedule.getTennisCourt() ) );
        scheduleDTO.setStartDateTime( schedule.getStartDateTime() );
        scheduleDTO.setEndDateTime( schedule.getEndDateTime() );

        return scheduleDTO;
    }

    protected Guest createReservationRequestDTOToGuest(CreateReservationRequestDTO createReservationRequestDTO) {
        if ( createReservationRequestDTO == null ) {
            return null;
        }

        Guest guest = new Guest();

        guest.setId( createReservationRequestDTO.getGuestId() );

        return guest;
    }

    protected Schedule createReservationRequestDTOToSchedule(CreateReservationRequestDTO createReservationRequestDTO) {
        if ( createReservationRequestDTO == null ) {
            return null;
        }

        Schedule schedule = new Schedule();

        schedule.setId( createReservationRequestDTO.getScheduleId() );

        return schedule;
    }
}
