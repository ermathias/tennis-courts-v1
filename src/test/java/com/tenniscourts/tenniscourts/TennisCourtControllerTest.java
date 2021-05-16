package com.tenniscourts.tenniscourts;

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

import com.tenniscourts.schedules.ScheduleDTO;

@ExtendWith(MockitoExtension.class)
public class TennisCourtControllerTest {

	private static final String TENNIS_COURT_NAME = "guest1";
	private static final Long TENNIS_COURT_ID = 1L;
	private static final Long SHEDULE_ID = 1L;
	private static final LocalDateTime START_DATE = LocalDateTime.now();
	private static final LocalDateTime END_DATE = START_DATE.plusHours(1);

	@InjectMocks
	private TennisCourtController tennisCourtController;

	@Mock
	private TennisCourtService tennisCourtService;

	private TennisCourtDTO tennisCourtDTO;

	@BeforeEach
	public void setUp() {
		tennisCourtDTO = TennisCourtDTO.builder().name(TENNIS_COURT_NAME).id(TENNIS_COURT_ID).build();
	}

	@Test
	public void shouldReturnTennisCourtDTO() {
		doReturn(tennisCourtDTO).when(tennisCourtService).findTennisCourtById(TENNIS_COURT_ID);
		ResponseEntity<TennisCourtDTO> responseEntity = tennisCourtController.findTennisCourtById(TENNIS_COURT_ID);
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		TennisCourtDTO actual = responseEntity.getBody();
		assertNotNull(actual);
		assertEquals(TENNIS_COURT_ID, actual.getId());
		assertEquals(TENNIS_COURT_NAME, actual.getName());

	}

	@Test
	public void shouldReturnTennisCourtWithSchedules() {
		List<ScheduleDTO> scheduleList = new ArrayList<>();
		scheduleList.add(buildSchedule());
		tennisCourtDTO.setTennisCourtSchedules(scheduleList);
		doReturn(tennisCourtDTO).when(tennisCourtService).findTennisCourtWithSchedulesById(TENNIS_COURT_ID);
		ResponseEntity<TennisCourtDTO> responseEntity = tennisCourtController
				.findTennisCourtWithSchedulesById(TENNIS_COURT_ID);
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		TennisCourtDTO actual = responseEntity.getBody();
		assertNotNull(actual);
		List<ScheduleDTO> actualScheduleList=actual.getTennisCourtSchedules();
		assertNotNull(actualScheduleList);
		ScheduleDTO scheduleDTO = actualScheduleList.get(0);
		assertEquals(SHEDULE_ID, scheduleDTO.getId());
		assertEquals(START_DATE, scheduleDTO.getStartDateTime());
		assertEquals(END_DATE, scheduleDTO.getEndDateTime());
		assertEquals(TENNIS_COURT_ID, actual.getId());
		assertEquals(TENNIS_COURT_NAME, actual.getName());
		
	}

	private ScheduleDTO buildSchedule() {
		ScheduleDTO scheduleDTO = new ScheduleDTO();
		scheduleDTO.setEndDateTime(END_DATE);
		scheduleDTO.setStartDateTime(START_DATE);
		scheduleDTO.setId(SHEDULE_ID);
		scheduleDTO.setTennisCourt(tennisCourtDTO);
		return scheduleDTO;
	}
}
