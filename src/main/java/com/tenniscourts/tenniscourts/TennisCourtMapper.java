package com.tenniscourts.tenniscourts;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Added ReportingPolicy.IGNORE because it was showing too many warnings when application started.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TennisCourtMapper {
    TennisCourtDTO map(TennisCourt source);

    @InheritInverseConfiguration
    TennisCourt map(TennisCourtDTO source);
}
