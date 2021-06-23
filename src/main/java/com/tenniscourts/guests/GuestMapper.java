package com.tenniscourts.guests;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GuestMapper {

    Guest map(GuestDTO source);

    @InheritInverseConfiguration
    GuestDTO map(Guest source);

    List<GuestDTO> map(List<Guest> source);

    @Mapping(target = "name", source = "guestName")
    Guest map(CreateGuestRequestDTO source);
}
