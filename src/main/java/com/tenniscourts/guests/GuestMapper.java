package com.tenniscourts.guests;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

import com.tenniscourts.reservations.CreateReservationRequestDTO;
import com.tenniscourts.reservations.Reservation;
import com.tenniscourts.reservations.ReservationDTO;

@Component
@Mapper(componentModel = "spring")
public interface GuestMapper {
	 Reservation map(ReservationDTO source);

	    @InheritInverseConfiguration
	    ReservationDTO map(Reservation source);

	    @Mappings({
	    @Mapping(target = "guest.id", source = "guestId"),
	    @Mapping(target = "schedule.id", source = "scheduleId")
	    })
	    Reservation map(CreateReservationRequestDTO source);
}
