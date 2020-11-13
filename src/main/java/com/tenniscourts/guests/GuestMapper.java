package com.tenniscourts.guests;


import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GuestMapper {

    Guest map(GuestDTO guest);

    GuestDTO map(Guest guest);

    List<GuestDTO> map(List<Guest> source);
}
