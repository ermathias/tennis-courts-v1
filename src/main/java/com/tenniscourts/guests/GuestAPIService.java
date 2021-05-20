package com.tenniscourts.guests;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@Api(value = "", tags = "Guests", description = "Guests REST Endpoints")
@RequestMapping(value = "/guests")
@Validated
public interface GuestAPIService {

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Adds a guest", nickname = "addGuest")
    default ResponseEntity<Void> addGuest(
            @ApiParam(value = "Guest data") @Valid @RequestBody CreateGuestRequestDTO createGuestDTO) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @GetMapping(value = "/{guestId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Returns a guest of a given id", nickname = "findGuestById")
    default ResponseEntity<GuestDTO> findGuestById(
            @PathVariable Long guestId) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @GetMapping(value = "/name/{guestName}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Returns a list of guests by a given name", nickname = "findGuestsByName")
    default ResponseEntity<List<GuestDTO>> findGuestsByName(
            @PathVariable String guestName) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @PutMapping(value = "/{guestId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Updates a guest name", nickname = "updateGuest")
    default ResponseEntity<Void> updateGuest(
            @PathVariable Long guestId,
            @ApiParam(value = "Guest data") @Valid @RequestBody CreateGuestRequestDTO createGuestDTO) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @DeleteMapping(value = "/{guestId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Deletes a guest", nickname = "deleteGuest")
    default ResponseEntity<GuestDTO> deleteGuest(
            @PathVariable Long guestId) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Returns all guests", nickname = "getGuests")
    default ResponseEntity<List<GuestDTO>> getGuests() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }
}
