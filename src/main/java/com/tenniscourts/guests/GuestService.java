package com.tenniscourts.guests;

import java.util.List;

/**
 * Guest interface
 */

public interface GuestService {

	/**
	 * Method to add guest
	 * 
	 * @param guestRequest
	 * @return {@link GuestDTO}
	 */
	GuestDTO addGuest(GuestRequest guestRequest);

	/**
	 * Method to modify guest
	 * 
	 * @param guestDTO
	 * @return {@link GuestDTO}
	 */
	GuestDTO modifyGuest(GuestDTO guestDTO);

	/**
	 * Method to find guest by id
	 * 
	 * @param guestId
	 * @return {@link GuestDTO}
	 */
	GuestDTO findGuestById(Long guestId);

	/**
	 * Method to find guest by name
	 * 
	 * @param name
	 * @return List of {@link GuestDTO}
	 */
	List<GuestDTO> findGuestByName(String name);

	/**
	 * Method to remove guest
	 * 
	 * @param guestId
	 * @return String
	 */
	String removeGuest(Long guestId);

	/**
	 * Method to find all guest
	 * 
	 * @return List of {@link GuestDTO}
	 */
	List<GuestDTO> findAllGuests();

}
