package com.tenniscourts.guests;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface GuestMapper {

	public static final GuestMapper GUEST_MAPPER_INSTANCE = Mappers.getMapper(GuestMapper.class);

	Guest map(GuestRequest guestRequest);

	GuestDTO map(Guest guest);

	List<GuestDTO> map(List<Guest> guests);

}
