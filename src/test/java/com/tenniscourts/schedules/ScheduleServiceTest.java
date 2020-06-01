package com.tenniscourts.schedules;

import java.time.LocalDateTime;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.tenniscourts.tenniscourts.TennisCourt;
import com.tenniscourts.tenniscourts.TennisCourtDTO;
import com.tenniscourts.tenniscourts.TennisCourtMapper;

public class ScheduleServiceTest {

	private static final LocalDateTime END_TIME = LocalDateTime.of(2010, 12, 31, 00, 59, 59, 999999999);
	private static final Long ANY_LONG = null;
	private static LocalDateTime START_TIME = LocalDateTime.of(2010, 12, 30, 23, 59, 59, 999999999);
	@Mock
	private ScheduleRepository repository;
	@Mock
	private ScheduleMapper mapper;
	@Mock
	private TennisCourtMapper tennisCourtMapper;
	@Mock
	private TennisCourtScheduleService tennisCourtScheduleService;
	private ScheduleService service;

	@Before
	public void setUp()
		throws Exception {
		MockitoAnnotations.initMocks(this);
		service = new ScheduleService(repository, mapper, tennisCourtMapper, tennisCourtScheduleService);
	}

	/**
	 * TODO Guarantee valid input ?
	 */
	@Test(expected = NullPointerException.class)
	public void addScheduleWithNoStartDate() {
		service.addSchedule(ANY_LONG, new CreateScheduleRequestDTO());
	}

	/**
	 * TODO Test designed to break if any behavior changes (change any property between operations).
	 */
	@Test
	public void addSchedule() {
		Schedule expectedSchedule = new Schedule();
		expectedSchedule.setTennisCourt(new TennisCourt());
		expectedSchedule.setStartDateTime(START_TIME);
		expectedSchedule.setEndDateTime(END_TIME);

		CreateScheduleRequestDTO request = new CreateScheduleRequestDTO();
		request.setStartDateTime(START_TIME);

		Mockito
			.when(tennisCourtScheduleService.findTennisCourt(ArgumentMatchers.anyLong()))
			.thenReturn(new TennisCourtDTO());
		Mockito
			.when(tennisCourtMapper.map(ArgumentMatchers.any(TennisCourtDTO.class)))
			.thenReturn(new TennisCourt());
		Mockito
			.when(repository.save(ArgumentMatchers.any(Schedule.class)))
			.thenReturn(new Schedule());
		Mockito
			.when(mapper.map(ArgumentMatchers.any(Schedule.class)))
			.thenReturn(new ScheduleDTO());

		ScheduleDTO result = service.addSchedule(0L, request);


		Mockito.verify(tennisCourtScheduleService).findTennisCourt(ArgumentMatchers.eq(0L));
		Mockito.verify(tennisCourtMapper).map(ArgumentMatchers.eq(new TennisCourtDTO()));
		Mockito.verify(repository).save(ArgumentMatchers.eq(expectedSchedule));
		Mockito.verify(mapper).map(ArgumentMatchers.eq(new Schedule()));

		Assert.assertEquals(new ScheduleDTO(), result);
	}
}
