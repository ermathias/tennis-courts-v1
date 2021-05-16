package com.tenniscourts.guests.schedules;

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

import com.tenniscourts.schedules.ScheduleController;
import com.tenniscourts.schedules.ScheduleDTO;
import com.tenniscourts.schedules.ScheduleService;
import com.tenniscourts.tenniscourts.TennisCourtDTO;

@ExtendWith(MockitoExtension.class)
public class ScheduleControllerTest {
	private static final Long SHEDULE_ID = 1L;
	private static final LocalDateTime START_DATE = LocalDateTime.now();
	private static final LocalDateTime END_DATE = START_DATE.plusHours(1);
	private static final String TENNIS_COURT_NAME = "guest1";
	private static final Long TENNIS_COURT_ID = 1L;

	@InjectMocks
	private ScheduleController scheduleController;

	@Mock
	private ScheduleService scheduleService;

	private ScheduleDTO scheduleDTO;
	private TennisCourtDTO tennisCourtDTO;

	@BeforeEach
	public void setUp() {
		tennisCourtDTO = TennisCourtDTO.builder().name(TENNIS_COURT_NAME).id(TENNIS_COURT_ID).build();
		scheduleDTO = buildScheduleDTO();
	}

	@Test
	public void shouldReturnScheduleDTOWhileFindSchedulesByDates() {
		List<ScheduleDTO> schedules = new ArrayList<>();
		schedules.add(scheduleDTO);
		doReturn(schedules).when(scheduleService).findSchedulesByDates(START_DATE, END_DATE);
		ResponseEntity<List<ScheduleDTO>> responseEntity = scheduleController.findSchedulesByDates(START_DATE,
				END_DATE);
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		ScheduleDTO actual = responseEntity.getBody().get(0);
		assertNotNull(actual);
		assertEquals(SHEDULE_ID, actual.getId());
		assertEquals(START_DATE, actual.getStartDateTime());
		assertEquals(END_DATE, actual.getEndDateTime());
		TennisCourtDTO tennisCourt = actual.getTennisCourt();
		assertNotNull(tennisCourt);
		assertEquals(TENNIS_COURT_ID, tennisCourt.getId());
		assertEquals(TENNIS_COURT_NAME, tennisCourt.getName());

	}

	@Test
	public void shouldReturnScheduleDTOWhileFindSchedulesById() {
		doReturn(scheduleDTO).when(scheduleService).findSchedule(SHEDULE_ID);
		ResponseEntity<ScheduleDTO> responseEntity = scheduleController.findByScheduleId(SHEDULE_ID);
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		ScheduleDTO actual = responseEntity.getBody();
		assertNotNull(actual);
		assertEquals(SHEDULE_ID, actual.getId());
		assertEquals(START_DATE, actual.getStartDateTime());
		assertEquals(END_DATE, actual.getEndDateTime());
		TennisCourtDTO tennisCourt = actual.getTennisCourt();
		assertNotNull(tennisCourt);
		assertEquals(TENNIS_COURT_ID, tennisCourt.getId());
		assertEquals(TENNIS_COURT_NAME, tennisCourt.getName());

	}

	private ScheduleDTO buildScheduleDTO() {
		ScheduleDTO scheduleDTO = new ScheduleDTO();
		scheduleDTO.setEndDateTime(END_DATE);
		scheduleDTO.setStartDateTime(START_DATE);
		scheduleDTO.setId(SHEDULE_ID);
		scheduleDTO.setTennisCourt(tennisCourtDTO);
		return scheduleDTO;
	}

}
