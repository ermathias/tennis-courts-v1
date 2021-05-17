package com.tenniscourts.reservations;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tenniscourts.exceptions.AlreadyExistsEntityException;
import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.exceptions.InvalidDateTimeException;
import com.tenniscourts.guests.Guest;
import com.tenniscourts.guests.GuestDTO;
import com.tenniscourts.guests.GuestService;
import com.tenniscourts.schedules.Schedule;
import com.tenniscourts.schedules.ScheduleDTO;
import com.tenniscourts.schedules.ScheduleService;
import com.tenniscourts.tenniscourts.TennisCourt;
import com.tenniscourts.tenniscourts.TennisCourtDTO;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {

	private static final Long SCHEDULE_ID = 1L;
	private static final Long NEW_SCHEDULE_ID = 2L;
	private static final LocalDateTime START_DATE = LocalDateTime.now().plusHours(5);
	private static final LocalDateTime END_DATE = START_DATE.plusHours(1);
	private static final String TENNIS_COURT_NAME = "guest1";
	private static final Long GUEST_ID = 1L;
	private static final Long RESERVATION_ID = 1L;
	private static final String GUEST_NAME = "guest1";
	private static final Long TENNIS_COURT_ID = 1L;

	@InjectMocks
	private ReservationService reservationService;

	@Mock
	private ReservationRepository reservationRepository;
	@Mock
	private GuestService guestService;
	@Mock
	private ScheduleService scheduleService;

	private CreateReservationRequestDTO createReservationRequestDTO;
	private Schedule schedule;
	private Guest guest;
	private Reservation reservation;
	private ScheduleDTO scheduleDTO;
	private TennisCourtDTO tennisCourtDTO;
	private GuestDTO guestDTO;

	@BeforeEach
	public void setUp() {
		schedule = buildSchedule();
		guest = buildGuest();
		createReservationRequestDTO = CreateReservationRequestDTO.builder().guestId(GUEST_ID).scheduleId(SCHEDULE_ID)
				.build();
		reservation = buildReservation();
		tennisCourtDTO = TennisCourtDTO.builder().name(TENNIS_COURT_NAME).id(TENNIS_COURT_ID).build();
		scheduleDTO = buildScheduleDTO();
		guestDTO = buildGuestDTO();

	}

	@Test
	public void shouldReturnReservationDTOWhenBookReservation() {
		doReturn(guestDTO).when(guestService).findGuestById(GUEST_ID);
		doReturn(scheduleDTO).when(scheduleService).findSchedule(SCHEDULE_ID);
		doReturn(Optional.empty()).when(reservationRepository).findByScheduleAndGuestAndReservationStatus(schedule, guest,ReservationStatus.READY_TO_PLAY);
		doReturn(reservation).when(reservationRepository).saveAndFlush(Mockito.any(Reservation.class));

		ReservationDTO actual = reservationService.bookReservation(createReservationRequestDTO);
		assertNotNull(actual);
	}

	@Test
	public void shouldReturnAlreadyExistWhenBookReservation() {
		doReturn(scheduleDTO).when(scheduleService).findSchedule(SCHEDULE_ID);
		doReturn(guestDTO).when(guestService).findGuestById(GUEST_ID);
		doReturn(Optional.of(reservation)).when(reservationRepository).findByScheduleAndGuestAndReservationStatus(schedule, guest,ReservationStatus.READY_TO_PLAY);
		try {
			reservationService.bookReservation(createReservationRequestDTO);
		} catch (AlreadyExistsEntityException ex) {
			assertNotNull(ex);
			assertEquals("This schedule already reserved for given guest", ex.getMessage());
		}

	}

	@Test
	public void shouldReturnReservationDTOwhenFindReservation() {

		doReturn(Optional.of(reservation)).when(reservationRepository).findById(RESERVATION_ID);
		ReservationDTO actual = reservationService.findReservation(RESERVATION_ID);
		assertNotNull(actual);
		assertReservation(actual, ReservationStatus.READY_TO_PLAY);
	}

	@Test
	public void shouldReturnEntityNotFoundwhenFindReservation() {

		doReturn(Optional.empty()).when(reservationRepository).findById(RESERVATION_ID);
		try {
			reservationService.findReservation(RESERVATION_ID);
		} catch (EntityNotFoundException ex) {
			assertNotNull(ex);
			assertEquals("Reservation not found.", ex.getMessage());
		}

	}

	@Test
	public void shouldReturnReservationDTOListwhenRetrieveHistory() {
		List<Reservation> reservations = new ArrayList<>();
		reservations.add(reservation);
		doReturn(reservations).when(reservationRepository).findAllByDateUpdateBetween(START_DATE, END_DATE);
		List<ReservationDTO> actualList = reservationService.retrieveHistory(START_DATE, END_DATE);
		assertNotNull(actualList);
		ReservationDTO actual = actualList.get(0);
		assertNotNull(actual);
		assertReservation(actual, ReservationStatus.READY_TO_PLAY);

	}

	@Test
	public void shouldReturnInvalidRangewhenRetrieveHistory() {
		List<Reservation> reservations = new ArrayList<>();
		reservations.add(reservation);
		LocalDateTime START_DATE_TIME = START_DATE.plusHours(10);
		try {
			reservationService.retrieveHistory(START_DATE_TIME, END_DATE);

		} catch (InvalidDateTimeException ex) {
			assertNotNull(ex);
			assertEquals("Please provide valid range", ex.getMessage());
		}

	}

	@Test
	public void shouldReturnReservationDTOwhenCancelReservation() {

		doReturn(Optional.of(reservation)).when(reservationRepository).findById(RESERVATION_ID);
		Reservation newReservation = buildReservation();
		newReservation.setReservationStatus(ReservationStatus.CANCELLED);
		doReturn(newReservation).when(reservationRepository).save(Mockito.any(Reservation.class));
		ReservationDTO actual = reservationService.cancelReservation(RESERVATION_ID);
		assertNotNull(actual);
		assertReservation(actual, ReservationStatus.CANCELLED);
	}

	@Test
	public void shouldReturnEntityNotFoundwhenCancelReservation() {

		doReturn(Optional.empty()).when(reservationRepository).findById(RESERVATION_ID);
		try {
			reservationService.cancelReservation(RESERVATION_ID);
		} catch (EntityNotFoundException ex) {
			assertNotNull(ex);
			assertEquals("Reservation not found.", ex.getMessage());
		}

	}

	@Test
	public void shouldReturnCannotReschedulewhenCancelReservation() {

		doReturn(Optional.of(reservation)).when(reservationRepository).findById(RESERVATION_ID);
		reservation.setReservationStatus(ReservationStatus.CANCELLED);
		try {
			reservationService.cancelReservation(RESERVATION_ID);
		} catch (IllegalArgumentException ex) {
			assertNotNull(ex);
			assertEquals("Cannot cancel/reschedule because it's not in ready to play status.", ex.getMessage());
		}

	}

	@Test
	public void shouldReturnCannotRescheduleInvalidDatewhenCancelReservation() {
		reservation.getSchedule().setStartDateTime(LocalDateTime.now().minusHours(2));
		doReturn(Optional.of(reservation)).when(reservationRepository).findById(RESERVATION_ID);
		try {
			reservationService.cancelReservation(RESERVATION_ID);
		} catch (IllegalArgumentException ex) {
			assertNotNull(ex);
			assertEquals("Can cancel/reschedule only future dates.", ex.getMessage());
		}

	}

	@Test
	public void shouldReturnCannotRescheduleSameSlotwhenRescheduleReservation() {

		doReturn(Optional.of(reservation)).when(reservationRepository).findById(RESERVATION_ID);
		Reservation newReservation = buildReservation();
		newReservation.setReservationStatus(ReservationStatus.CANCELLED);
		doReturn(newReservation).when(reservationRepository).save(Mockito.any(Reservation.class));
		try {
			reservationService.rescheduleReservation(RESERVATION_ID, SCHEDULE_ID);
		} catch (IllegalArgumentException ex) {
			assertNotNull(ex);
			assertEquals("Cannot reschedule to the same slot.", ex.getMessage());
		}

	}

	@Test
	public void shouldReturnReservationDTOwhenRescheduleReservation() {

		doReturn(Optional.of(reservation)).when(reservationRepository).findById(RESERVATION_ID);
		Reservation newReservation = buildReservation();
		newReservation.setReservationStatus(ReservationStatus.CANCELLED);
		doReturn(newReservation).when(reservationRepository).save(Mockito.any(Reservation.class));
		Schedule neSchedule = buildSchedule();
		neSchedule.setId(NEW_SCHEDULE_ID);
		doReturn(guestDTO).when(guestService).findGuestById(GUEST_ID);
		doReturn(scheduleDTO).when(scheduleService).findSchedule(NEW_SCHEDULE_ID);
		doReturn(Optional.empty()).when(reservationRepository).findByScheduleAndGuestAndReservationStatus(schedule, guest,ReservationStatus.READY_TO_PLAY);
		Reservation reScheduledReservation = buildReservation();
		reScheduledReservation.setReservationStatus(ReservationStatus.RESCHEDULED);
		doReturn(reScheduledReservation).when(reservationRepository).saveAndFlush(Mockito.any(Reservation.class));
		ReservationDTO actual = reservationService.rescheduleReservation(RESERVATION_ID, NEW_SCHEDULE_ID);
		assertNotNull(actual);
		assertEquals(RESERVATION_ID, actual.getId());
		assertEquals(BigDecimal.TEN, actual.getValue());
		assertEquals(BigDecimal.TEN, actual.getRefundValue());
		assertEquals(ReservationStatus.RESCHEDULED.name(), actual.getReservationStatus());
		assertNotNull(actual.getSchedule());
		assertEquals(SCHEDULE_ID, actual.getSchedule().getId());
		assertEquals(GUEST_ID, actual.getGuestId());
	}

	private Reservation buildReservation() {
		Reservation reservation = new Reservation();
		reservation.setId(RESERVATION_ID);
		reservation.setGuest(buildGuest());
		reservation.setSchedule(buildSchedule());
		reservation.setRefundValue(BigDecimal.TEN);
		reservation.setValue(BigDecimal.TEN);
		reservation.setReservationStatus(ReservationStatus.READY_TO_PLAY);
		return reservation;
	}

	private Guest buildGuest() {
		Guest guest = new Guest();
		guest.setName(GUEST_NAME);
		guest.setId(GUEST_ID);
		return guest;
	}

	private Schedule buildSchedule() {
		Schedule schedule = new Schedule();
		schedule.setEndDateTime(END_DATE);
		schedule.setStartDateTime(START_DATE);
		schedule.setId(SCHEDULE_ID);
		TennisCourt tennisCourt = new TennisCourt();
		tennisCourt.setId(TENNIS_COURT_ID);
		tennisCourt.setName(TENNIS_COURT_NAME);
		schedule.setTennisCourt(tennisCourt);
		return schedule;
	}

	private ScheduleDTO buildScheduleDTO() {
		ScheduleDTO scheduleDTO = new ScheduleDTO();
		scheduleDTO.setEndDateTime(END_DATE);
		scheduleDTO.setStartDateTime(START_DATE);
		scheduleDTO.setId(SCHEDULE_ID);
		scheduleDTO.setTennisCourt(tennisCourtDTO);
		return scheduleDTO;
	}

	private GuestDTO buildGuestDTO() {
		GuestDTO guestDTO = new GuestDTO();
		guestDTO.setName(GUEST_NAME);
		guestDTO.setId(GUEST_ID);
		return guestDTO;
	}

	private void assertReservation(ReservationDTO actual, ReservationStatus status) {
		assertEquals(RESERVATION_ID, actual.getId());
		assertEquals(BigDecimal.TEN, actual.getValue());
		assertEquals(BigDecimal.TEN, actual.getRefundValue());
		assertEquals(status.name(), actual.getReservationStatus());
		assertNotNull(actual.getSchedule());
		assertEquals(SCHEDULE_ID, actual.getSchedule().getId());
		assertEquals(GUEST_ID, actual.getGuestId());
	}

}