package com.tenniscourts.reservations;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
//@Mapper(implementationPackage = “mapper.impl”)
public interface ReservationMapper {

    Reservation map(ReservationDTO source);

    @InheritInverseConfiguration
    ReservationDTO map(Reservation source);
    
    @Mappings({
    @Mapping(target = "guest.id", source = "source.guestId"),
   @Mapping(target = "schedule.id", source = "source.scheduleId")
    })
    Reservation map(CreateReservationRequestDTO source);
}
