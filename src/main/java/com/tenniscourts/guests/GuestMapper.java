package com.tenniscourts.guests;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GuestMapper {

    Guest map(GuestDTO source);

    @InheritInverseConfiguration
    GuestDTO map(Guest source);
    
    List<GuestDTO> map(List<Guest> source);
    
    Guest map(CreateGuestRequestDTO source);
}
