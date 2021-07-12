package com.tenniscourts.guests;

import com.tenniscourts.reservations.CreateReservationRequestDTO;
import com.tenniscourts.reservations.Reservation;
import com.tenniscourts.reservations.ReservationDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GuestMapper {

    Guest map(GuestDTO source);

    @InheritInverseConfiguration
    GuestDTO map(Guest source);
}
