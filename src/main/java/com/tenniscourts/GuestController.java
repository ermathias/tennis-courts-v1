/**
 * 
 */
package com.tenniscourts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tenniscourts.config.BaseRestController;
import com.tenniscourts.guests.Guest;
import com.tenniscourts.guests.GuestService;
import com.tenniscourts.tenniscourts.TennisCourtDTO;
import com.tenniscourts.tenniscourts.TennisCourtService;

import lombok.AllArgsConstructor;

/**
 * @author 6045094
 *
 */
@EnableAutoConfiguration
@RestController
public class GuestController{

	@Autowired
    private  GuestService guestService;

	@RequestMapping(value="/relationships", method = RequestMethod.POST, produces = { "application/json" })
	public ResponseEntity<String> createRelationship(@RequestBody  com.tenniscourts.guests.Guest guest)
	{
		try{
		guestService.save(guest);
		}
	catch (Exception e) {
	//	logger.error(e.getMessage());
		return new ResponseEntity(guest, HttpStatus.BAD_REQUEST);
	}
	return new ResponseEntity(guest, HttpStatus.CREATED);
	}
	@RequestMapping(value="/hello", method = RequestMethod.GET)
	public String hello()
	{
		return "Hello";
	}
}
