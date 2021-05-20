package com.tenniscourts.guests;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GuestMapper {
    GuestDTO map(Guest source);

    @InheritInverseConfiguration
    Guest map(GuestDTO source);
}
