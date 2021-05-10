package com.tenniscourts.guests;

import org.jvnet.hk2.annotations.Service;
import org.springframework.data.repository.CrudRepository;

@Service
public interface GuestService extends CrudRepository<Guest, Long> {

	
	public Guest save(Guest guest);
}
