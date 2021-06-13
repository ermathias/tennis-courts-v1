package com.tenniscourts.guests;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GuestMapper {

    Guest map(GuestDTO source);

    GuestDTO map(Guest source);

    Guest map(CreateGuestRequestDTO source);
}
