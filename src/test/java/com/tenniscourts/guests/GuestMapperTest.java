package com.tenniscourts.guests;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import com.tenniscourts.guests.to.GuestDTO;
import com.tenniscourts.guests.to.GuestUpdateDTO;


public class GuestMapperTest {

	private GuestMapper mapper;

	@Before
	public void setup() {
		mapper = Mappers.getMapper(GuestMapper.class);
	}

	@Test
	public void fromEntity() {
		Guest entity = new Guest();
		entity.setId(0L);
		entity.setName("guest 00");

		GuestDTO expected = GuestDTO.builder()
				.id(0L)
				.name("guest 00")
				.build();

		GuestDTO result = mapper.fromEntity(entity);
		Assert.assertEquals(expected, result);
	}

	@Test
	public void toEntity() {
		GuestDTO dto = GuestDTO.builder()
				.id(1L)
				.name("guest 01")
				.build();

		Guest expected = new Guest();
		expected.setId(1L);
		expected.setName("guest 01");

		Guest result = mapper.toEntity(dto);
		Assert.assertEquals(expected, result);
	}

	@Test
	public void toEntityAsCollection() {
		Guest entity = new Guest();
		entity.setId(2L);
		entity.setName("guest 02");
		List<Guest> entities = Arrays.asList(entity);

		Collection<GuestDTO> expected = Arrays.asList(GuestDTO.builder()
			.id(2L)
			.name("guest 02")
			.build());

		Collection<GuestDTO> result = mapper.fromEntity(entities);
		Assert.assertEquals(expected, result);
	}

	@Test
	public void updateEntity() {
		Guest entity = new Guest();
		entity.setId(3L);
		entity.setName("guest 03");

		GuestUpdateDTO dto = GuestUpdateDTO.builder()
				.name("new guest 03")
				.build();

		Guest expected = new Guest();
		expected.setId(3L);
		expected.setName("new guest 03");

		Guest result = mapper.updateEntity(entity, dto);
		Assert.assertEquals(expected, result);
	}
}
