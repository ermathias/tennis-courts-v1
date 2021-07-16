package com.tenniscourts.model.schedule;

import com.tenniscourts.dto.CreateReservationRequestDTO;
import com.tenniscourts.dto.CreateScheduleRequestDTO;
import com.tenniscourts.dto.ScheduleDTO;
import com.tenniscourts.model.reservation.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {

    Schedule map(ScheduleDTO source);

    ScheduleDTO map(Schedule source);

    List<ScheduleDTO> map(List<Schedule> source);

    @Mapping(target = "tennisCourt.id", source = "tennisCourtId")
    Schedule map(CreateScheduleRequestDTO source);
}
