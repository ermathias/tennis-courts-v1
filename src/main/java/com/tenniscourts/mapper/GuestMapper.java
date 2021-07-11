package com.tenniscourts.mapper;

import com.tenniscourts.dto.GuestDTO;
import com.tenniscourts.model.Guest;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GuestMapper {

    Guest map(GuestDTO source);

    GuestDTO map(Guest source);

    List<GuestDTO> map(List<Guest> source);
}
