package com.tenniscourts.guests;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GuestMapper {
    List<GuestRequestDTO> map(List<Guest> source);

    GuestRequestDTO map(Guest source);

    @InheritInverseConfiguration
    Guest map(GuestRequestDTO source);


}
