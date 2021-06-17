package com.tenniscourts.guests;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GuestMapper {

    Guest map(GuestDTO source);

    @InheritInverseConfiguration
    GuestDTO map(Guest source);

    List<GuestDTO> map(List<Guest> source);
}
