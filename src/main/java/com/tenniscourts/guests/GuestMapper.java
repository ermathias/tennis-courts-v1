package com.tenniscourts.guests;

import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring")
public interface GuestMapper {

    Guest map(GuestUserDTO user);

    GuestUserDTO map(Guest user);

    List<GuestUserDTO> map(List<Guest> user);
}
