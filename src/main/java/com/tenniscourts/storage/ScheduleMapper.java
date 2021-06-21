package com.tenniscourts.storage;

import com.tenniscourts.model.Schedule;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ScheduleMapper {

    Schedule map(ScheduleDTO source);

    @InheritInverseConfiguration
    ScheduleDTO map(Schedule source);

    List<ScheduleDTO> map(List<Schedule> source);
}

