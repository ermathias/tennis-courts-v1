package com.tenniscourts.guests;

import org.springframework.stereotype.Component;

@Component
public class GuestMapperImpl implements GuestMapper{

	@Override
	public Guest map(GuestDTO source) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GuestDTO map(Guest source) {
		if (source == null) {
            return null;
        }

		GuestDTO guestDTO = new GuestDTO();
		guestDTO.setName(source.getName());
        return guestDTO;
	}

	@Override
	public Guest map(CreateGuestRequestDTO source) {
		// TODO Auto-generated method stub
		return null;
	}

}
