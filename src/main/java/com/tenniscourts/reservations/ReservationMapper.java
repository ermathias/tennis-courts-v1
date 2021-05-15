package com.tenniscourts.reservations;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ReservationMapper {

	public static final ReservationMapper RESERVATION_MAPPER_INSTANCE = Mappers.getMapper(ReservationMapper.class);

	Reservation map(ReservationDTO source);

	@Mapping(target = "guestId", source = "guest.id")
    @Mapping(target = "scheduledId", source = "schedule.id")
	ReservationDTO map(Reservation source);

	Reservation map(CreateReservationRequestDTO source);
}
