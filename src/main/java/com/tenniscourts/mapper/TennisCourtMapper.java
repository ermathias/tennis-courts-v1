package com.tenniscourts.mapper;

import com.tenniscourts.dto.ScheduleDTO;
import com.tenniscourts.dto.TennisCourtDTO;
import com.tenniscourts.model.Schedule;
import com.tenniscourts.model.TennisCourt;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TennisCourtMapper {
    TennisCourtDTO map(TennisCourt source);

    @InheritInverseConfiguration
    TennisCourt map(TennisCourtDTO source);

    List<TennisCourtDTO> map(List<TennisCourt> source);
}
