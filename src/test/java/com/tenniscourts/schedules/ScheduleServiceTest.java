package com.tenniscourts.schedules;

import java.time.LocalDateTime;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.tenniscourts.guests.GuestMapper;

public class ScheduleServiceTest {

	private static final Long ANY_LONG = null;
	private static LocalDateTime NOW = LocalDateTime.of(2010, 12, 30, 23, 59, 59, 999999999);
	@Mock
	private ScheduleRepository repository;
	@Mock
	private GuestMapper mapper;
	@Mock
	private TennisCourtScheduleService tennisCourtScheduleService;
	private ScheduleService service;

	@Before
	public void setUp()
		throws Exception {
		MockitoAnnotations.initMocks(this);
		service = new ScheduleService(repository, mapper, tennisCourtScheduleService);
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
		ScheduleDTO expected = new ScheduleDTO();
		expected.setTennisCourtId(0L);
		expected.setStartDateTime(NOW);
		expected.setEndDateTime(LocalDateTime.of(2010, 12, 31, 00, 59, 59, 999999999));
		CreateScheduleRequestDTO request = new CreateScheduleRequestDTO();
		request.setStartDateTime(NOW);

		Mockito
			.when(mapper.map(ArgumentMatchers.any(ScheduleDTO.class)))
			.thenReturn(new Schedule());
		Mockito
			.when(repository.save(ArgumentMatchers.any(Schedule.class)))
			.thenReturn(new Schedule());
		Mockito
			.when(mapper.map(ArgumentMatchers.any(Schedule.class)))
			.thenReturn(new ScheduleDTO());

		ScheduleDTO result = service.addSchedule(0L, request);

		Mockito.verify(mapper).map(ArgumentMatchers.eq(expected));
		Mockito.verify(repository).save(ArgumentMatchers.eq(new Schedule()));

		Assert.assertEquals(new ScheduleDTO(), result);
	}
}
