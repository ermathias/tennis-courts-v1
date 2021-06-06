package com.tenniscourts.guests;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GuestMapper {
    GuestDTO map(Guest source);

    @InheritInverseConfiguration
    Guest map(GuestDTO source);
}