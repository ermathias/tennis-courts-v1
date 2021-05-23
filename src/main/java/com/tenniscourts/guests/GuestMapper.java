package com.tenniscourts.guests;

import com.tenniscourts.schedules.Schedule;
import com.tenniscourts.schedules.ScheduleDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GuestMapper {

    GuestDTO guestToGuestDTO(Guest source);

    List<GuestDTO> guestsToGuestDTOs(List<Guest> source);

    NewGuestDTO guestDTOtoNewGuestDTO(GuestDTO source);

    Guest newGuestDTOToGuest(NewGuestDTO source);
}
