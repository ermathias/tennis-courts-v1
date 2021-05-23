package com.tenniscourts.guests;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.tenniscourts.exceptions.BookingSlotNotAvailableException;
import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.reservations.Reservation;
import com.tenniscourts.reservations.ReservationService;
import com.tenniscourts.schedules.Schedule;
import com.tenniscourts.schedules.ScheduleDTO;
import com.tenniscourts.schedules.ScheduleService;
import com.tenniscourts.tenniscourts.TennisCourtDTO;
import com.tenniscourts.tenniscourts.TennisCourtMapper;
import com.tenniscourts.tenniscourts.TennisCourtService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GuestService {

	private ScheduleService scheduleService;

	private TennisCourtService tennisCourtService;

	private GuestRepository guestRepository;

	private ReservationService reservationService;

	private TennisCourtMapper tennisCourtMapper;

	public TennisCourtDTO bookReservation(@Valid GuestReservationDTO guestReservationDTO) {
		List<GuestDTO> guestDTOList = guestReservationDTO.getGuestDTOList();
		Guest guest = isGuestAvailable(guestReservationDTO.getGuestId());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		if (null != guestDTOList) {
			List<Reservation> reservationList = new ArrayList<>();
			for (GuestDTO guestDTO : guestDTOList) {
				LocalDateTime startDate = LocalDateTime.parse(guestDTO.getStartDate(), formatter);
				LocalDateTime endDate = LocalDateTime.parse(guestDTO.getEndDate(), formatter);
				TennisCourtDTO tennisCourtDTO = checkBookingAvailable(guestDTO.getTennisCourtId(), startDate, endDate);
				Schedule schedule = new Schedule();
				schedule.setTennisCourt(tennisCourtMapper.map(tennisCourtDTO));
				schedule.setStartDateTime(LocalDateTime.parse(guestDTO.getStartDate(), formatter));
				schedule.setEndDateTime(LocalDateTime.parse(guestDTO.getEndDate(), formatter));

				Reservation reservation = new Reservation();
				reservation.setGuest(guest);
				reservation.setSchedule(schedule);
				reservation.setValue(BigDecimal.TEN);
				reservation.setRefundValue(BigDecimal.TEN);
				reservationList.add(reservation);
			}
			reservationService.bookReservation(reservationList);
		}

		return null;
	}

	private TennisCourtDTO checkBookingAvailable(Long tennisCourtId, LocalDateTime startDate, LocalDateTime endDate) {
		TennisCourtDTO findTennisCourtById = tennisCourtService.findTennisCourtById(tennisCourtId);
		List<ScheduleDTO> findSchedulesByTennisCourtIdStartDateAndEndDate = scheduleService
				.findSchedulesByTennisCourtIdStartDateAndEndDate(tennisCourtId, startDate, endDate);
		if (!CollectionUtils.isEmpty(findSchedulesByTennisCourtIdStartDateAndEndDate)) {
			throw new BookingSlotNotAvailableException(
					"Booking slot not available for tenniscourtid:" + tennisCourtId + " at start time:" + startDate);
		}
		return findTennisCourtById;
	}

	private Guest isGuestAvailable(Long id) {
		Optional<Guest> guestOptional = guestRepository.findById(id);
		if (guestOptional.isPresent()) {
			return guestOptional.get();
		} else {
			throw new EntityNotFoundException("Guest with id:" + id + " not found");
		}
	}

}
