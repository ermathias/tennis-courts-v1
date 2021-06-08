package com.tenniscourts.guests;

public class GuestMapperImpl implements GuestMapper {

	@Override
	public GuestDTO map(Guest source) {
		GuestDTO guest = new GuestDTO(source.getId(), source.getName());
		return guest;
	}

	@Override
	public Guest map(GuestDTO source) {
		Guest guest = new Guest(source.getName());
		guest.setId(source.getId());
		return guest;
	}

	@Override
	public Guest map(CreateUpdateGuestRequestDTO source) {
		Guest guest = new Guest(source.getName());
		guest.setId(source.getId());
		return guest;
	}

}
