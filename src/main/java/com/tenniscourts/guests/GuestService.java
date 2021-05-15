package com.tenniscourts.guests;

import java.util.List;

/**
 * Guest interface
 */

public interface GuestService {

	GuestDTO addGuest(GuestRequest guestRequest);

	GuestDTO modifyGuest(GuestDTO guestDTO);

	GuestDTO findGuestById(Long guestId);

	List<GuestDTO> findGuestByName(String name);

	String removeGuest(Long guestId);

	List<GuestDTO> findAllGuests();

}
