package com.tenniscourts.reservations;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tenniscourts.exceptions.BusinessException;
import com.tenniscourts.guests.Guest;
import com.tenniscourts.schedules.Schedule;
import com.tenniscourts.schedules.ScheduleRepository;
import com.tenniscourts.tenniscourts.TennisCourt;
import com.tenniscourts.tenniscourts.TennisCourtRepository;

@Repository
@Transactional
public class ReservationDAOImp {

	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	private TennisCourtRepository tennisCourtRepository;

	@Autowired
	private ScheduleRepository scheduleRepository;

	public ReservationDTO bookReservation(CreateReservationRequestDTO createReservationRequestDTO) {
		ReservationDTO reservationDTO = null;

		Guest guest = entityManager.find(Guest.class, createReservationRequestDTO.getGuestId());
		Schedule schedule = entityManager.find(Schedule.class, createReservationRequestDTO.getScheduleId());

		Reservation reservation;
		Long id = null;
		
		if(guest !=null) {

		if (schedule != null) {
			reservation = new Reservation();

			reservation.setGuest(guest);
			reservation.setValue(new BigDecimal("100.0"));
			reservation.setRefundValue(new BigDecimal("0"));
			reservation.setSchedule(schedule);
			reservation.setReservationStatus(ReservationStatus.READY_TO_PLAY);

			schedule.addReservation(reservation);

			entityManager.persist(schedule);
			entityManager.persist(reservation);

			id = reservation.getId();

			if (id != null) {
				reservationDTO = new ReservationDTO();
				reservationDTO.setId(1L);
				reservationDTO.setReservationStatus(ReservationStatus.READY_TO_PLAY.toString());
				reservationDTO.setValue(reservation.getValue());
				reservationDTO.setGuestId(guest.getId());
				reservationDTO.setScheduledId(schedule.getId());

			}
		}
		}
		else {
			throw new EntityNotFoundException("No Guest found with the given Id");
		}

		return reservationDTO;
	}

	public Boolean checkForSlotAvailability(CreateReservationRequestDTO createReservationRequestDTO) {

		Schedule schedule = entityManager.find(Schedule.class, createReservationRequestDTO.getScheduleId());

		if (schedule != null) {
			List<Reservation> reservationList = schedule.getReservations();
			if (reservationList == null || reservationList.isEmpty() || reservationList.size() < 2) {
				for (Reservation reservation : reservationList) {
					if (reservation.getGuest().getId() == createReservationRequestDTO.getGuestId()) {
						throw new BusinessException("Guest is  already booked in the given time slot.");
					}
				}
				return true;
			}
		}
		else {
			throw new EntityNotFoundException("No Schedule found with the given Id");
		}

		return false;
	}

}
