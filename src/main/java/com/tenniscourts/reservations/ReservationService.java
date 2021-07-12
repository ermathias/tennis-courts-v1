package com.tenniscourts.reservations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.guests.Guest;
import com.tenniscourts.guests.GuestDTO;
import com.tenniscourts.guests.GuestService;
import com.tenniscourts.schedules.Schedule;
import com.tenniscourts.schedules.ScheduleDTO;
import com.tenniscourts.schedules.ScheduleService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ReservationService {

	@Autowired
	private GuestService guestService;

	@Autowired
	private ScheduleService scheduleService;

	private final ReservationRepository reservationRepository;

	private ModelMapper mapper = new ModelMapper();

	private void timeSlotCheck(Long scheduleId, LocalDateTime startDateTime, LocalDateTime endDateTime) {
		reservationRepository.findByScheduleIdAndStartDateTimeAndEndDateTime(scheduleId, startDateTime, endDateTime)
				.map(reservation -> {
					throw new IllegalArgumentException(
							"Time slot already scheduled. Reservation id " + reservation.getId());
				});
	}

	private void timeSlotCheck(Long scheduleId) {
		ScheduleDTO scheduleDTO = scheduleService.findSchedule(scheduleId);
		timeSlotCheck(scheduleId, scheduleDTO.getStartDateTime(), scheduleDTO.getEndDateTime());
	}

	public ReservationDTO bookReservation(CreateReservationRequestDTO createReservationRequestDTO) {

		GuestDTO guestDTO = guestService.retrieveById(createReservationRequestDTO.getGuestId());

		ScheduleDTO scheduleDTO = scheduleService.findSchedule(createReservationRequestDTO.getScheduleId());

		timeSlotCheck(createReservationRequestDTO.getScheduleId(), createReservationRequestDTO.getStartDateTime(),
				createReservationRequestDTO.getEndDateTime());

		Reservation reservation = Reservation.builder().guest(mapper.map(guestDTO, Guest.class))
				.schedule(mapper.map(scheduleDTO, Schedule.class)).value(createReservationRequestDTO.getValue())
				.refundValue(new BigDecimal(10.0)).startDateTime(createReservationRequestDTO.getStartDateTime())
				.endDateTime(createReservationRequestDTO.getEndDateTime()).build();

		return mapper.map(reservationRepository.save(reservation), ReservationDTO.class);

	}

	public List<ReservationDTO> findReservations() {
		return reservationRepository.findAll().stream()
				.map(reservation -> mapper.map(reservation, ReservationDTO.class)).collect(Collectors.toList());
	}

	public ReservationDTO findReservation(Long reservationId) {
		return reservationRepository.findById(reservationId).map(reservation -> {

			return mapper.map(reservation, ReservationDTO.class);

		}).orElseThrow(() -> {
			throw new EntityNotFoundException("Reservation not found.");
		});
	}

	public ReservationDTO cancelReservation(Long reservationId) {
		return mapper.map(cancel(reservationId), ReservationDTO.class);
	}

	private Reservation cancel(Long reservationId) {
		return reservationRepository.findById(reservationId).map(reservation -> {

			this.validateCancellation(reservation);

			BigDecimal refundValue = getRefundValue(reservation);
			return this.updateReservation(reservation, refundValue, ReservationStatus.CANCELLED);

		}).orElseThrow(() -> {
			throw new EntityNotFoundException("Reservation not found.");
		});
	}

	private Reservation updateReservation(Reservation reservation, BigDecimal refundValue, ReservationStatus status) {
		reservation.setReservationStatus(status);
		reservation.setValue(reservation.getValue().subtract(refundValue));
		reservation.setRefundValue(refundValue);

		return reservationRepository.save(reservation);
	}

	private void validateCancellation(Reservation reservation) {
		if (!ReservationStatus.READY_TO_PLAY.equals(reservation.getReservationStatus())) {
			throw new IllegalArgumentException("Cannot cancel/reschedule because it's not in ready to play status.");
		}

		if (reservation.getSchedule().getStartDateTime().isBefore(LocalDateTime.now())) {
			throw new IllegalArgumentException("Can cancel/reschedule only future dates.");
		}
	}

	public static BigDecimal percentage(BigDecimal base, BigDecimal pct) {
		return base.multiply(pct).divide(new BigDecimal(100));
	}

	public BigDecimal getRefundValue(Reservation reservation) {
		long hours = ChronoUnit.HOURS.between(LocalDateTime.now(), reservation.getSchedule().getStartDateTime());

		if (hours >= 24) {
			return reservation.getValue();
		}

		if (hours >= 12) {
			return percentage(reservation.getValue(), new BigDecimal(25));
		}

		if (hours >= 2) {
			return percentage(reservation.getValue(), new BigDecimal(50));
		}

		if (hours >= 0) {
			return percentage(reservation.getValue(), new BigDecimal(75));
		}

		return BigDecimal.ZERO;
	}

	/*
	 * DONE: This method actually not fully working, find a way to fix the issue
	 * when it's throwing the error: "Cannot reschedule to the same slot.
	 */
	public ReservationDTO rescheduleReservation(Long previousReservationId, Long scheduleId) {

		timeSlotCheck(scheduleId);

		Reservation previousReservation = cancel(previousReservationId);

		previousReservation.setReservationStatus(ReservationStatus.RESCHEDULED);
		reservationRepository.save(previousReservation);

		ReservationDTO newReservation = bookReservation(CreateReservationRequestDTO.builder()
				.guestId(previousReservation.getGuest().getId()).scheduleId(scheduleId)
				.startDateTime(previousReservation.getStartDateTime()).endDateTime(previousReservation.getEndDateTime())
				.value(previousReservation.getValue()).build());
		newReservation.setPreviousReservation(mapper.map(previousReservation, ReservationDTO.class));
		return newReservation;
	}
}
