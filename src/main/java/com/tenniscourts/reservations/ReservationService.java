package com.tenniscourts.reservations;

import com.tenniscourts.exceptions.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
@AllArgsConstructor
public class ReservationService {

    private ReservationRepository reservationRepository;

    private  ReservationMapper reservationMapper;

    public ReservationDTO bookReservation(CreateReservationRequestDTO createReservationRequestDTO) {
        throw new UnsupportedOperationException();
    }

    public ReservationDTO findReservation(Long reservationId) {
        return reservationRepository.findById(reservationId).map(reservationMapper::map).orElseThrow(() -> {
            throw new EntityNotFoundException("Reservation not found.");
        });
    }

    public ReservationDTO cancelReservation(Long reservationId) {
        return reservationMapper.map(this.cancel(reservationId));
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

    public BigDecimal getRefundValue(Reservation reservation) {
        long hours = ChronoUnit.HOURS.between(LocalDateTime.now(), reservation.getSchedule().getStartDateTime());

        LocalTime now = LocalTime.now();
		if (hours >= 24) {
			return reservation.getValue();
		} else if (now.isBefore(LocalTime.MAX) && now.isAfter(LocalTime.NOON)) {
			return reservation.getValue().multiply(new BigDecimal(25)).divide(new BigDecimal(100));
		} else if (now.isBefore(LocalTime.NOON) && now.isAfter(LocalTime.of(2, 0))) {
			return reservation.getValue().multiply(new BigDecimal(50)).divide(new BigDecimal(100));
		} else if (now.isBefore(LocalTime.of(2, 0)) && now.isAfter(LocalTime.MIN)) {
			return reservation.getValue().multiply(new BigDecimal(75)).divide(new BigDecimal(100));
		}

		return BigDecimal.ZERO;

    }
    
    public ReservationDTO rescheduleReservation(Long previousReservationId, Long scheduleId) {

		Optional<Reservation> previousReservation = reservationRepository.findById(previousReservationId);

		if (previousReservation.isPresent()) {

			if (scheduleId.equals(previousReservation.get().getSchedule().getId())) {
				throw new IllegalArgumentException("Cannot reschedule to the same slot.");
			} else {
				Reservation reservation = cancel(previousReservationId);
				reservation.setReservationStatus(ReservationStatus.RESCHEDULED);
				reservationRepository.save(reservation);

				ReservationDTO newReservation = bookReservation(CreateReservationRequestDTO.builder()
						.guestId(reservation.getGuest().getId()).scheduleId(scheduleId).build());
				newReservation.setPreviousReservation(reservationMapper.map(reservation));
				return newReservation;
			}

		}
		return null;

	}
}