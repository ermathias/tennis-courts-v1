package com.tenniscourts.guests;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GuestMapper {

    GuestDTO map(Guest source);

    Guest map(GuestDTO source);

    Guest map(CreateGuestRequestDTO source);

}
