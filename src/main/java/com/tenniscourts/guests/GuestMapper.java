package com.tenniscourts.guests;

import java.util.List;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GuestMapper {
	
	Guest map(CreateGuestDTO source);
	
	GuestDTO map(Guest source);
	
	Guest map(GuestDTO source);
	
	List<GuestDTO> map(List<Guest> source);

}
