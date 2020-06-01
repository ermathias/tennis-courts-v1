package com.tenniscourts.schedules;

import java.time.LocalDateTime;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import com.tenniscourts.tenniscourts.TennisCourt;
import com.tenniscourts.tenniscourts.TennisCourtDTO;


public class ScheduleMapperTest {

	private static LocalDateTime START_DATE = LocalDateTime.of(2010, 12, 30, 23, 59, 59, 999999999);

	private ScheduleMapper mapper;

	@Before
	public void setUp()
		throws Exception {
		mapper = Mappers.getMapper(ScheduleMapper.class);
	}

	@Test
	public void mapToEntity() {
		TennisCourtDTO tennisCourt = new TennisCourtDTO();
		ScheduleDTO source = new ScheduleDTO();
		source.setStartDateTime(START_DATE);
		source.setTennisCourt(tennisCourt);

		Schedule expected = new Schedule();
		expected.setStartDateTime(START_DATE);
		expected.setTennisCourt(new TennisCourt());

		Schedule result = mapper.map(source);

		Assert.assertEquals(expected, result);
	}

}
