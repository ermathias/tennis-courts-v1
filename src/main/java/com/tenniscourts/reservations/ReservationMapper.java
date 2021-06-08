package com.tenniscourts.reservations;


import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Service;

import com.tenniscourts.schedules.Schedule;

@Service
@Mapper(componentModel = "spring")
public interface ReservationMapper {

    Reservation map(ReservationDTO source);

    @InheritInverseConfiguration
    ReservationDTO map(Reservation source);

    @Mappings({
    	@Mapping(target = "guest.id", source = "guestId"),
    	@Mapping(target = "schedule.id", source = "scheduleId")
	})
    Reservation map(CreateReservationRequestDTO source, Schedule schedule);
}
