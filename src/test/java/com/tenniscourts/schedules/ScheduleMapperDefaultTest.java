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


public class ScheduleMapperDefaultTest {

	private static LocalDateTime START_DATE = LocalDateTime.of(2010, 12, 30, 23, 59, 59, 999999999);
	private static LocalDateTime END_DATE = LocalDateTime.of(2010, 12, 31, 00, 59, 59, 999999999);

	@Mock
	private TennisCourtMapper tennisCourtMapper;
	private ScheduleMapperDefault mapper;

	@Before
	public void setUp()
		throws Exception {
		MockitoAnnotations.initMocks(this);
		mapper = new ScheduleMapperDefault(tennisCourtMapper);
	}

	@Test
	public void mapToEntity() {
		TennisCourtDTO tennisCourt = new TennisCourtDTO();
		ScheduleDTO source = new ScheduleDTO();
		source.setStartDateTime(START_DATE);
		source.setTennisCourt(tennisCourt);

		Schedule expected = new Schedule();
		expected.setStartDateTime(START_DATE);
		expected.setEndDateTime(END_DATE);
		expected.setTennisCourt(new TennisCourt());

		TennisCourt returnTennisCourt = new TennisCourt();

		Mockito.when(
			tennisCourtMapper.map(ArgumentMatchers.any(TennisCourtDTO.class))
		).thenReturn(returnTennisCourt);

		Schedule result = mapper.map(source);

		Mockito
			.verify(tennisCourtMapper)
			.map(source.getTennisCourt());

		Assert.assertEquals(expected, result);
	}

}
