package com.tenniscourts.reservations;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class ReservationControllerTest {

	private static final Long NEW_SCHEDULE_ID = 2L;
	private static final LocalDateTime START_DATE = LocalDateTime.now().plusHours(5);
	private static final LocalDateTime END_DATE = START_DATE.plusHours(1);
	private static final Long RESERVATION_ID = 1L;

	@InjectMocks
	private ReservationController reservationController;

	@Mock
	private ReservationService reservationService;

	private ReservationDTO reservationDTO;

	@BeforeEach
	public void setUp() {
		reservationDTO = buildReservationDTO();
	}

	@Test
	public void shouldReturnReservationDTOFindReservation() {
		doReturn(reservationDTO).when(reservationService).findReservation(RESERVATION_ID);
		ResponseEntity<ReservationDTO> responseEntity = reservationController.findReservation(RESERVATION_ID);
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		ReservationDTO actual = responseEntity.getBody();
		assertNotNull(actual);
		assertEquals(RESERVATION_ID, actual.getId());
	}

	@Test
	public void shouldReturnReservationDTOCancelReservation() {
		doReturn(reservationDTO).when(reservationService).cancelReservation(RESERVATION_ID);
		ResponseEntity<ReservationDTO> responseEntity = reservationController.cancelReservation(RESERVATION_ID);
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		ReservationDTO actual = responseEntity.getBody();
		assertNotNull(actual);
		assertEquals(RESERVATION_ID, actual.getId());
	}

	@Test
	public void shouldReturnReservationDTORescheduleReservation() {
		doReturn(reservationDTO).when(reservationService).rescheduleReservation(RESERVATION_ID, NEW_SCHEDULE_ID);
		ResponseEntity<ReservationDTO> responseEntity = reservationController.rescheduleReservation(RESERVATION_ID,
				NEW_SCHEDULE_ID);
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		ReservationDTO actual = responseEntity.getBody();
		assertNotNull(actual);
		assertEquals(RESERVATION_ID, actual.getId());
	}

	@Test
	public void shouldReturnReservationDTORetrieveHistory() {
		List<ReservationDTO> reservations = new ArrayList<>();
		reservations.add(reservationDTO);
		doReturn(reservations).when(reservationService).retrieveHistory(START_DATE, END_DATE);
		ResponseEntity<List<ReservationDTO>> responseEntity = reservationController.retrieveHistory(START_DATE,
				END_DATE);
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		ReservationDTO actual = responseEntity.getBody().get(0);
		assertNotNull(actual);
		assertEquals(RESERVATION_ID, actual.getId());
	}

	private ReservationDTO buildReservationDTO() {
		return ReservationDTO.builder().id(RESERVATION_ID).build();
	}
}
