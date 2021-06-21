package com.tenniscourts.controller;

import com.tenniscourts.config.BaseRestController;
import com.tenniscourts.storage.CreateGuestDTO;
import com.tenniscourts.storage.GuestDTO;
import com.tenniscourts.service.GuestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Api(value = "GuestController", tags = {"Guest"})
@RestController
@RequestMapping("/v1/guest")
@AllArgsConstructor
public class GuestController extends BaseRestController {

    private final GuestService guestService;

    @ApiOperation(httpMethod = "GET", value = "Get value",notes = "Get guest by ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Request completed successfully"),
            @ApiResponse(code = 400, message = "There was a validation issue with the supplied request body"),
            @ApiResponse(code = 404, message = "No session was provided or could be found for the reservation"),
            @ApiResponse(code = 500, message = "Internal Error")})
    @GetMapping("/{id}")
    public ResponseEntity<GuestDTO> findGuestById(@PathVariable Long id) {
        return ResponseEntity.ok(guestService.findGuestById(id));
    }

    @ApiOperation(httpMethod = "GET", value = "Get value",notes = "Get all guests")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Request completed successfully"),
            @ApiResponse(code = 400, message = "There was a validation issue with the supplied request body"),
            @ApiResponse(code = 404, message = "No session was provided or could be found for the guest"),
            @ApiResponse(code = 500, message = "Internal Error")})
    @GetMapping("/all")
    public ResponseEntity<List<GuestDTO>> findAllGuests() {
        return ResponseEntity.ok(guestService.findAllGuests());
    }

    @ApiOperation(httpMethod = "GET", value = "Get value",notes = "Get guest by name")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Request completed successfully"),
            @ApiResponse(code = 400, message = "There was a validation issue with the supplied request body"),
            @ApiResponse(code = 404, message = "No session was provided or could be found for the guest"),
            @ApiResponse(code = 500, message = "Internal Error")})
    @GetMapping("/name/{name}")
    public ResponseEntity<List<GuestDTO>> findGuestByName(@PathVariable String name) {
        return ResponseEntity.ok(guestService.findGuestByName(name));
    }

    @ApiOperation(httpMethod = "POST", value = "Post value",notes = "Add new guest")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Request completed successfully"),
            @ApiResponse(code = 400, message = "There was a validation issue with the supplied request body"),
            @ApiResponse(code = 404, message = "No session was provided or could be found for the guest"),
            @ApiResponse(code = 500, message = "Internal Error")})
    @PostMapping
    public ResponseEntity<Void> addGuest(@RequestBody CreateGuestDTO guest) {
        return ResponseEntity.created(locationByEntity(guestService.addGuest(guest).getId())).build();
    }

    @ApiOperation(httpMethod = "PUT", value = "Put value",notes = "Update guest")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Request completed successfully"),
            @ApiResponse(code = 400, message = "There was a validation issue with the supplied request body"),
            @ApiResponse(code = 404, message = "No session was provided or could be found for the guest"),
            @ApiResponse(code = 500, message = "Internal Error")})
    @PutMapping
    public ResponseEntity<Void> updateGuest(@RequestBody GuestDTO guest) {
        return ResponseEntity.created(locationByEntity(guestService.updateGuest(guest).getId())).build();
    }

    @ApiOperation(httpMethod = "DELETE", value = "Delete",notes = "Update guest")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Request completed successfully"),
            @ApiResponse(code = 400, message = "There was a validation issue with the supplied request body"),
            @ApiResponse(code = 404, message = "No session was provided or could be found for the guest"),
            @ApiResponse(code = 500, message = "Internal Error")})
    @DeleteMapping("/id")
    public ResponseEntity<GuestDTO> deleteGuest(@PathVariable Long id){
        return ResponseEntity.ok(guestService.deleteGuest(id));
    }
}
