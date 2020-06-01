package com.tenniscourts.guests;

import java.util.Collection;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.tenniscourts.guests.to.GuestCreateDTO;
import com.tenniscourts.guests.to.GuestDTO;
import com.tenniscourts.guests.to.GuestUpdateDTO;

@Mapper(
	componentModel = "spring")
public interface GuestMapper {

	Guest toEntity(
		GuestDTO dto);

	@InheritInverseConfiguration
	GuestDTO fromEntity(
		Guest entity);

	Collection<GuestDTO> fromEntity(
		Collection<Guest> guests);

	Guest toEntity(
		GuestCreateDTO dto);



	@Mapping(source = "entity.id", target = "id")
    @Mapping(source = "dto.name", target = "name")
	Guest updateEntity(Guest entity, GuestUpdateDTO dto);
}
