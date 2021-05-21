package com.tenniscourts.guests;

import com.tenniscourts.reservations.CreateReservationRequestDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GuestsMapper {

    Guest map(CreateReservationRequestDTO source);

    Guest map(GuestDTO source);

    @InheritInverseConfiguration
    GuestDTO map(Guest source);

    Guest map(CreateGuestRequestDTO source);
}
