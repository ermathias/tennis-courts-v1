package com.tenniscourts.guests;

import static com.tenniscourts.utils.TennisCourtsConstraints.CREATE_PATH;
import static com.tenniscourts.utils.TennisCourtsConstraints.DELETE_PATH;
import static com.tenniscourts.utils.TennisCourtsConstraints.FINDBYID_PATH;
import static com.tenniscourts.utils.TennisCourtsConstraints.FINDBYNAME_PATH;
import static com.tenniscourts.utils.TennisCourtsConstraints.GUEST_API_PATH;
import static com.tenniscourts.utils.TennisCourtsConstraints.LIST_PATH;
import static com.tenniscourts.utils.TennisCourtsConstraints.NOT_FOUND_GUEST;
import static com.tenniscourts.utils.TennisCourtsConstraints.UPDATE_PATH;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tenniscourts.config.BaseRestController;
import com.tenniscourts.guests.dto.CreateGuestRequestDTO;
import com.tenniscourts.guests.dto.GuestDTO;
import com.tenniscourts.guests.dto.UpdateGuestRequestDTO;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
@RequestMapping(path = GUEST_API_PATH)
public class GuestController extends BaseRestController {

    private final GuestService guestService;

    @ApiResponse(code = 200, message = "Guest created")
    @PostMapping(CREATE_PATH)
    public ResponseEntity<Long> createGuest( @Valid @RequestBody CreateGuestRequestDTO createGuestDTO, BindingResult bindingResult) {
    	validateInputs(bindingResult);
    	Long createdGuestId = guestService.create(createGuestDTO).getId();
		return ResponseEntity.created( locationByEntity(createdGuestId))
        		.body(createdGuestId);
    }
    
    @ApiResponses({
    	@ApiResponse(code = 200, message = "Guest updated"),
   	 	@ApiResponse(code = 404, message = NOT_FOUND_GUEST)
    })
    @PutMapping(UPDATE_PATH)
    public ResponseEntity<Void> updateGuest(@Valid @RequestBody UpdateGuestRequestDTO dto, BindingResult bindingResult) {
    	validateInputs(bindingResult);
    	this.guestService.update(dto);
    	return ResponseEntity.ok().build();
    }
    
    @ApiResponses({
    	@ApiResponse(code = 200, message = "Guest deleted"),
   	 	@ApiResponse(code = 404, message = NOT_FOUND_GUEST)
    })
    @DeleteMapping(DELETE_PATH + "/{guestId}")
    public ResponseEntity<Void> deleteGuest(@PathVariable Long guestId) {
    	this.guestService.delete(guestId);
    	return ResponseEntity.ok().build();
    }
    
    @ApiResponses({
    	@ApiResponse(code = 200, message = "Guest found"),
   	 	@ApiResponse(code = 404, message = NOT_FOUND_GUEST)
    })
    @GetMapping(FINDBYID_PATH + "/{guestId}")
    public ResponseEntity<GuestDTO> findGuestById(@PathVariable Long guestId) {
    	return ResponseEntity.ok(this.guestService.findById(guestId));
    }
    
    @ApiResponses({
    	@ApiResponse(code = 200, message = "Guest found"),
   	 	@ApiResponse(code = 404, message = NOT_FOUND_GUEST)
    })
    @GetMapping(FINDBYNAME_PATH + "/{guestName}")
    public ResponseEntity<List<GuestDTO>> findGuestByName(@PathVariable String guestName) {
    	return ResponseEntity.ok(this.guestService.findByName(guestName));
    }
    
    @GetMapping(LIST_PATH)
    public ResponseEntity<List<GuestDTO>> list() {
    	return ResponseEntity.ok(this.guestService.list());
    }
    
}
