package com.tenniscourts.reservations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.tenniscourts.exceptions.AlreadyExistsEntityException;
import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.exceptions.InvalidDateTimeException;
import com.tenniscourts.guests.GuestMapper;
import com.tenniscourts.guests.GuestService;
import com.tenniscourts.schedules.ScheduleMapper;
import com.tenniscourts.schedules.ScheduleService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Reservation Service
 * 
 */
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ReservationService {

	private final ReservationRepository reservationRepository;
	private final GuestService guestService;
	private final ScheduleService scheduleService;
	private static final BigDecimal RESERVATION_DEPOSIT = BigDecimal.TEN;

	/**
	 * Method to book reservation
	 * 
	 * @param createReservationRequestDTO
	 * @return {@link ReservationDTO}
	 */
	public ReservationDTO bookReservation(CreateReservationRequestDTO createReservationRequestDTO) {
		return ReservationMapper.RESERVATION_MAPPER_INSTANCE
				.map(reservationRepository.saveAndFlush(checkReservationExist(Reservation.builder()
						.schedule(ScheduleMapper.SCHEDULE_MAPPER_INSTANCE
								.map(scheduleService.findSchedule(createReservationRequestDTO.getScheduleId())))
						.guest(GuestMapper.GUEST_MAPPER_INSTANCE
								.map(guestService.findGuestById(createReservationRequestDTO.getGuestId())))
						.reservationStatus(ReservationStatus.READY_TO_PLAY).value(RESERVATION_DEPOSIT)
						.refundValue(RESERVATION_DEPOSIT).build())));
	}

	/**
	 * Method to find reservation
	 * 
	 * @param reservationId
	 * @return {@link ReservationDTO}
	 */
	public ReservationDTO findReservation(Long reservationId) {
		return reservationRepository.findById(reservationId).map(ReservationMapper.RESERVATION_MAPPER_INSTANCE::map)
				.orElseThrow(() -> new EntityNotFoundException("Reservation not found."));
	}

	/**
	 * Method to cancel reservation
	 * 
	 * @param reservationId
	 * @return {@link ReservationDTO}
	 */
	public ReservationDTO cancelReservation(Long reservationId) {
		return ReservationMapper.RESERVATION_MAPPER_INSTANCE.map(this.cancel(reservationId));
	}

	/**
	 * Method to reschedule reservation
	 * 
	 * @param previousReservationId
	 * @param scheduleId
	 * @return {@link ReservationDTO}
	 */
	public ReservationDTO rescheduleReservation(Long previousReservationId, Long scheduleId) {
		Reservation previousReservation = cancel(previousReservationId);

		if (scheduleId.equals(previousReservation.getSchedule().getId())) {
			throw new IllegalArgumentException("Cannot reschedule to the same slot.");
		}

		previousReservation.setReservationStatus(ReservationStatus.RESCHEDULED);
		reservationRepository.save(previousReservation);

		ReservationDTO newReservation = bookReservation(CreateReservationRequestDTO.builder()
				.guestId(previousReservation.getGuest().getId()).scheduleId(scheduleId).build());
		newReservation.setPreviousReservation(ReservationMapper.RESERVATION_MAPPER_INSTANCE.map(previousReservation));
		return newReservation;
	}

	/**
	 * Method to retrieve reservation history
	 * 
	 * @param fromDate
	 * @param toDate
	 * @return List of {@link ReservationDTO}
	 */
	public List<ReservationDTO> retrieveHistory(LocalDateTime fromDate, LocalDateTime toDate) {
		if (fromDate.isAfter(toDate)) {
			throw new InvalidDateTimeException("Please provide valid range");
		}
		return ReservationMapper.RESERVATION_MAPPER_INSTANCE
				.map(reservationRepository.findAllByDateUpdateBetween(fromDate, toDate));
	}

	/**
	 * Method to check existence of reservation
	 * 
	 * @param reservation
	 * @return {@link Reservation}
	 */
	private Reservation checkReservationExist(Reservation reservation) {
		log.info("Checking reservation existence");
		if (reservation.getSchedule().getStartDateTime().isBefore(LocalDateTime.now())) {
			throw new IllegalArgumentException("Given schedule is expired");
		}
		Optional<Reservation> reservationOptional = reservationRepository.findByScheduleAndGuestAndReservationStatus(
				reservation.getSchedule(), reservation.getGuest(), ReservationStatus.READY_TO_PLAY);
		if (reservationOptional.isPresent()) {
			log.info("Reservation already existing");
			throw new AlreadyExistsEntityException("This schedule already reserved for given guest");
		}
		return reservation;
	}

	/**
	 * Method to update reservation
	 * 
	 * @param reservation
	 * @param refundValue
	 * @param status
	 * @return {@link Reservation}
	 */
	private Reservation updateReservation(Reservation reservation, BigDecimal refundValue, ReservationStatus status) {
		log.info("Updating reservation");
		reservation.setReservationStatus(status);
		reservation.setValue(reservation.getValue().subtract(refundValue));
		reservation.setRefundValue(refundValue);
		return reservationRepository.save(reservation);
	}

	/**
	 * Method to validate cancellation
	 * 
	 * @param reservation
	 */
	private void validateCancellation(Reservation reservation) {
		log.info("validating reservation");
		if (!ReservationStatus.READY_TO_PLAY.equals(reservation.getReservationStatus())) {
			log.info("Cannot cancel/reschedule because it's not in ready to play status.");
			throw new IllegalArgumentException("Cannot cancel/reschedule because it's not in ready to play status.");
		}

		if (reservation.getSchedule().getStartDateTime().isBefore(LocalDateTime.now())) {
			log.info("Can cancel/reschedule only future dates.");
			throw new IllegalArgumentException("Can cancel/reschedule only future dates.");
		}
	}

	/**
	 * Method to cancel reservation for given Id
	 * 
	 * @param reservationId
	 * @return {@link Reservation}
	 */
	private Reservation cancel(Long reservationId) {
		return reservationRepository.findById(reservationId).map(reservation -> {

			this.validateCancellation(reservation);

			BigDecimal refundValue = getRefundValue(reservation);
			return this.updateReservation(reservation, refundValue, ReservationStatus.CANCELLED);

		}).orElseThrow(() -> new EntityNotFoundException("Reservation not found."));
	}

	/**
	 * Method to calculate the refund value
	 * 
	 * @param reservation
	 * @return {@link BigDecimal}
	 */
	private BigDecimal getRefundValue(Reservation reservation) {
		long hours = ChronoUnit.HOURS.between(LocalDateTime.now(), reservation.getSchedule().getStartDateTime());

		if (hours >= 24) {
			return reservation.getValue();
		} else if (hours < 24 && hours >= 12) {
			return reservation.getValue().multiply(new BigDecimal(0.75));
		} else if (hours < 12 && hours >= 2) {
			return reservation.getValue().multiply(new BigDecimal(0.5));
		} else if (hours < 2 && hours >= 0) {
			return reservation.getValue().multiply(new BigDecimal(0.25));
		} else {
			return BigDecimal.ZERO;
		}
	}

}
