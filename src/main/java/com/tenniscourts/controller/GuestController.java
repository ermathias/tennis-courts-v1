package com.tenniscourts.controller;

import com.tenniscourts.config.BaseRestController;
import com.tenniscourts.dto.GuestDTO;
import com.tenniscourts.service.GuestService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GuestController extends BaseRestController {

    private final GuestService guestService;

    public GuestController(final GuestService guestService){
        this.guestService = guestService;
    }


    @ApiOperation("Create guest")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "A guest has been created successfully"),
            @ApiResponse(code = 400, message = "Bad request. Check your input"),
            @ApiResponse(code = 500, message = "An error has occurred in creating the guest")
    })
    @RequestMapping(value = "/api/guests/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createGuest(@RequestBody GuestDTO guestDTO) {
        return ResponseEntity.created(locationByEntity(guestService.create(guestDTO).getId())).build();
    }

    @ApiOperation("Get guest by ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "A guest has been successfully retrieved"),
            @ApiResponse(code = 400, message = "Bad request. Check your input"),
            @ApiResponse(code = 500, message = "An error has occurred in getting a guest by ID")
    })
    @RequestMapping(value = "/api/guests/get", method = RequestMethod.GET)
    public ResponseEntity<GuestDTO> findGuest(@RequestParam Long guestId) {
        return ResponseEntity.ok(guestService.findById(guestId));
    }

    @ApiOperation("Get guest by Name")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "A guest has been successfully retrieved"),
            @ApiResponse(code = 400, message = "Bad request. Check your input"),
            @ApiResponse(code = 500, message = "An error has occurred in getting a guest by ID")
    })
    @RequestMapping(value = "/api/guests/get-by-name", method = RequestMethod.GET)
    public ResponseEntity<GuestDTO> findGuestByName(@RequestParam String name) {
        return ResponseEntity.ok(guestService.findByName(name));
    }

    @ApiOperation("Cancel guest by ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "A guest has been successfully deleted"),
            @ApiResponse(code = 400, message = "Bad request. Check your input"),
            @ApiResponse(code = 500, message = "An error has occurred in deleting a guest")
    })
    @RequestMapping(value = "/api/guests/delete", method = RequestMethod.DELETE)
    public ResponseEntity<GuestDTO> cancelGuest(@RequestParam Long guestId) {
        return ResponseEntity.ok(guestService.cancel(guestId));
    }


    @ApiOperation("Modify guest")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "A guest has been successfully changed"),
            @ApiResponse(code = 400, message = "Bad request. Check your input"),
            @ApiResponse(code = 500, message = "An error has occurred in modifying a guest")
    })
    @RequestMapping(value = "/api/guests/modify", method = RequestMethod.PUT)
    public ResponseEntity<GuestDTO> modifyGuest(@RequestBody GuestDTO guestDTO) {
        return ResponseEntity.ok(guestService.modify(guestDTO));
    }


    @ApiOperation("Get all guests")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "A list of all guest has been successfully returned"),
            @ApiResponse(code = 400, message = "Bad request. Check your input"),
            @ApiResponse(code = 500, message = "An error has occurred in getting the list of guests")
    })
    @RequestMapping(value = "/api/guests/get-all", method = RequestMethod.GET)
    public ResponseEntity<List<GuestDTO>> findAllGuests() {
        return ResponseEntity.ok(guestService.findAll());
    }



}
