package com.tenniscourts.guests;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import com.tenniscourts.guests.dto.CreateGuestRequestDTO;
import com.tenniscourts.guests.dto.GuestDTO;
import com.tenniscourts.guests.dto.UpdateGuestRequestDTO;

@Mapper(componentModel = "spring")
public interface GuestMapper {
	
	Guest mapToEntity(GuestDTO source);

    @InheritInverseConfiguration
    GuestDTO mapToDTO(Guest source);

    Guest mapToEntity(CreateGuestRequestDTO source);
    Guest mapToEntity(UpdateGuestRequestDTO source);
    
}