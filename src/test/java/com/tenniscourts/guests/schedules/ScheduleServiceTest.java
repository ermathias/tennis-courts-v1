package com.tenniscourts.guests.schedules;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

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

import com.tenniscourts.schedules.CreateScheduleRequestDTO;
import com.tenniscourts.schedules.Schedule;
import com.tenniscourts.schedules.ScheduleDTO;
import com.tenniscourts.schedules.ScheduleRepository;
import com.tenniscourts.schedules.ScheduleService;
import com.tenniscourts.tenniscourts.TennisCourt;
import com.tenniscourts.tenniscourts.TennisCourtDTO;
import com.tenniscourts.tenniscourts.TennisCourtRepository;

@ExtendWith(MockitoExtension.class)
public class ScheduleServiceTest {
	private static final Long SHEDULE_ID = 1L;
	private static final LocalDateTime START_DATE = LocalDateTime.now().plusHours(5);
	private static final LocalDateTime END_DATE = START_DATE.plusHours(1);
	private static final String TENNIS_COURT_NAME = "guest1";
	private static final Long TENNIS_COURT_ID = 1L;

	@InjectMocks
	private ScheduleService scheduleService;

	@Mock
	private ScheduleRepository scheduleRepository;

	@Mock
	private TennisCourtRepository tennisCourtRepository;

	private TennisCourt tennisCourt;
	private Schedule schedule;
	private CreateScheduleRequestDTO createScheduleRequestDTO;

	@BeforeEach
	public void setUp() {
		schedule = buildSchedule();
		createScheduleRequestDTO = buildCreateScheduleRequestDTO();
		tennisCourt = buildTennisCourt();
	}

	@Test
	public void shouldReturnScheduleDTOWhileAddSchedule() {
		List<Schedule> schedules = new ArrayList<>();
		Schedule existSchedule = buildSchedule();
		existSchedule.setStartDateTime(START_DATE.plusDays(2));
		schedules.add(existSchedule);
		doReturn(schedules).when(scheduleRepository).findByTennisCourt_IdOrderByStartDateTime(TENNIS_COURT_ID);
		doReturn(Optional.of(tennisCourt)).when(tennisCourtRepository).findById(TENNIS_COURT_ID);
		doReturn(schedule).when(scheduleRepository).saveAndFlush(Mockito.any(Schedule.class));
		ScheduleDTO actual = scheduleService.addSchedule(TENNIS_COURT_ID, createScheduleRequestDTO);
		assertNotNull(actual);
		assertEquals(SHEDULE_ID, actual.getId());
		assertEquals(START_DATE, actual.getStartDateTime());
		assertEquals(END_DATE, actual.getEndDateTime());
		TennisCourtDTO tennisCourt = actual.getTennisCourt();
		assertNotNull(tennisCourt);
		assertEquals(TENNIS_COURT_ID, tennisCourt.getId());
		assertEquals(TENNIS_COURT_NAME, tennisCourt.getName());

	}

	private CreateScheduleRequestDTO buildCreateScheduleRequestDTO() {
		CreateScheduleRequestDTO createSchedule = new CreateScheduleRequestDTO();
		createSchedule.setStartDateTime(START_DATE);
		createSchedule.setTennisCourtId(TENNIS_COURT_ID);
		return createSchedule;
	}

	private Schedule buildSchedule() {
		Schedule schedule = new Schedule();
		schedule.setEndDateTime(END_DATE);
		schedule.setStartDateTime(START_DATE);
		schedule.setId(SHEDULE_ID);
		TennisCourt tennisCourt = new TennisCourt();
		tennisCourt.setId(TENNIS_COURT_ID);
		tennisCourt.setName(TENNIS_COURT_NAME);
		schedule.setTennisCourt(tennisCourt);
		return schedule;
	}

	private TennisCourt buildTennisCourt() {
		TennisCourt tennisCourt = new TennisCourt();
		tennisCourt.setId(TENNIS_COURT_ID);
		tennisCourt.setName(TENNIS_COURT_NAME);
		return tennisCourt;
	}
}
