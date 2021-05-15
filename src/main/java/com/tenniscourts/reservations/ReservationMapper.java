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

	@InheritInverseConfiguration
	ReservationDTO map(Reservation source);

	@Mapping(target = "guest.id", source = "guestId")
	@Mapping(target = "schedule.id", source = "scheduleId")
	Reservation map(CreateReservationRequestDTO source);
}
