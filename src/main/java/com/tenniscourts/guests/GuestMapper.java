package com.tenniscourts.guests;


import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * Added ReportingPolicy.IGNORE because it was showing too many warnings when application started.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GuestMapper {

    Guest map(GuestDTO guest);

    GuestDTO map(Guest guest);

    List<GuestDTO> map(List<Guest> source);
}
