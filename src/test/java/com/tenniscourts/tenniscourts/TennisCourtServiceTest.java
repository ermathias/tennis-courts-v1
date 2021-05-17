package com.tenniscourts.tenniscourts;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

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

import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.schedules.ScheduleDTO;
import com.tenniscourts.schedules.ScheduleService;

@ExtendWith(MockitoExtension.class)
public class TennisCourtServiceTest {
	private static final String TENNIS_COURT_NAME = "Court1";
	private static final Long TENNIS_COURT_ID = 1L;
	private static final String TENNIS_COURT_NOT_FOUND = "Tennis Court not found.";
	private static final Long SHEDULE_ID = 1L;
	private static final LocalDateTime START_DATE = LocalDateTime.now();
	private static final LocalDateTime END_DATE = START_DATE.plusHours(1);

	@InjectMocks
	private TennisCourtService tennisCourtService;

	@Mock
	TennisCourtRepository tennisCourtRepository;

	@Mock
	ScheduleService scheduleService;

	private TennisCourtDTO tennisCourtDTO;
	private TennisCourt tennisCourt;
	private TennisCourtRequest tennisCourtRequest;

	@BeforeEach
	public void setUp() {
		tennisCourtDTO = TennisCourtDTO.builder().name(TENNIS_COURT_NAME).id(TENNIS_COURT_ID).build();
		tennisCourtRequest = buildTennisCourtRequest();
		tennisCourt = buildTennisCourt();
	}

	@Test
	public void shouldReurnTennisCourtDTOWhileAddTennisCourt() {

		doReturn(tennisCourt).when(tennisCourtRepository).saveAndFlush(Mockito.any(TennisCourt.class));
		TennisCourtDTO actual = tennisCourtService.addTennisCourt(tennisCourtRequest);
		assertNotNull(actual);
		assertEquals(TENNIS_COURT_ID, actual.getId());
		assertEquals(TENNIS_COURT_NAME, actual.getName());

	}

	@Test
	public void shouldReurnTennisCourtDTOWhileFindTennisCourtById() {

		doReturn(Optional.of(tennisCourt)).when(tennisCourtRepository).findById(TENNIS_COURT_ID);
		TennisCourtDTO actual = tennisCourtService.findTennisCourtById(TENNIS_COURT_ID);
		assertNotNull(actual);
		assertEquals(TENNIS_COURT_ID, actual.getId());
		assertEquals(TENNIS_COURT_NAME, actual.getName());

	}

	@Test
	public void shouldEntityNotFoundWhileFindTennisCourtById() {
		doThrow(new EntityNotFoundException(TENNIS_COURT_NOT_FOUND)).when(tennisCourtRepository)
				.findById(TENNIS_COURT_ID);
		try {
			tennisCourtService.findTennisCourtById(TENNIS_COURT_ID);
		} catch (Exception ex) {
			assertNotNull(ex);
			assertEquals(TENNIS_COURT_NOT_FOUND, ex.getMessage());
		}

	}

	@Test
	public void shouldReurnTennisCourtDTOWhileFindTennisCourtWithSchedulesById() {
		List<ScheduleDTO> scheduleList = new ArrayList<>();
		scheduleList.add(buildSchedule());
		tennisCourtDTO.setTennisCourtSchedules(scheduleList);
		doReturn(Optional.of(tennisCourt)).when(tennisCourtRepository).findById(TENNIS_COURT_ID);
		doReturn(scheduleList).when(scheduleService).findSchedulesByTennisCourtId(TENNIS_COURT_ID);
		TennisCourtDTO actual = tennisCourtService.findTennisCourtWithSchedulesById(TENNIS_COURT_ID);
		assertNotNull(actual);
		List<ScheduleDTO> actualScheduleList = actual.getTennisCourtSchedules();
		assertNotNull(actualScheduleList);
		ScheduleDTO scheduleDTO = actualScheduleList.get(0);
		assertEquals(SHEDULE_ID, scheduleDTO.getId());
		assertEquals(START_DATE, scheduleDTO.getStartDateTime());
		assertEquals(END_DATE, scheduleDTO.getEndDateTime());
		assertEquals(TENNIS_COURT_ID, actual.getId());
		assertEquals(TENNIS_COURT_NAME, actual.getName());

	}

	private TennisCourt buildTennisCourt() {
		TennisCourt tennisCourt = new TennisCourt();
		tennisCourt.setId(1L);
		tennisCourt.setName(TENNIS_COURT_NAME);
		return tennisCourt;
	}

	private TennisCourtRequest buildTennisCourtRequest() {
		TennisCourtRequest tennisCourtRequest = new TennisCourtRequest();
		tennisCourtRequest.setName(TENNIS_COURT_NAME);
		return tennisCourtRequest;
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
