package com.tenniscourts.guests;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GuestMapper {

    GuestDTOs map(Guest source);

    @InheritInverseConfiguration
    Guest map(GuestDTOs source);

    List<GuestDTOs> map(List<Guest> source);
}
