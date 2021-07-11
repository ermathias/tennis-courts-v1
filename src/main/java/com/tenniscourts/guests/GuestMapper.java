package com.tenniscourts.guests;

public interface GuestMapper {

	Guest map(GuestDTO source);

	GuestDTO map(Guest source);

	Guest map(CreateGuestRequestDTO source);
}
