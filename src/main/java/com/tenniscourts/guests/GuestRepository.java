package com.tenniscourts.guests;
	
	import java.util.List;

	import org.springframework.data.jpa.repository.JpaRepository;

	@Repository
	public interface GuestRepository  extends JpaRepository<Guest, String> {
		 Optional<List<Guest>> findGuestByName(String name);
		
	}


