package com.tenniscourts.controller;

import com.tenniscourts.config.BaseRestController;
import com.tenniscourts.dto.GuestDTO;
import com.tenniscourts.service.GuestService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("guest")
@AllArgsConstructor
public class GuestController extends BaseRestController {

    private final GuestService guestService;

    @ApiOperation(value = "Add guest", notes = "This method add a guest")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Guest added!"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<Void> addGuest(
            @ApiParam(
                    name = "guest entity",
                    type = "GuestDTO",
                    value = "Model of guest that will be saved",
                    required = true)
            @RequestBody GuestDTO guestDTO) {
        return ResponseEntity.created(locationByEntity(guestService.addGuest(guestDTO).getId())).build();
    }

    @ApiOperation(value = "Update guest", notes = "This method update a guest")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Guest patched!"),
    })
    @PutMapping
    public ResponseEntity<Void> patchGuest(
            @ApiParam(
                    name = "guest entity",
                    type = "GuestDTO",
                    value = "Model of guest that will be updated",
                    required = true)
            @RequestBody GuestDTO guestDTO) {
        return ResponseEntity.created(locationByEntity(guestService.patchGuest(guestDTO).getId())).build();
    }

    @ApiOperation(value = "Delete guest", notes = "This method delete a guest")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Guest deleted!"),
    })
    @DeleteMapping
    public ResponseEntity<String> deleteGuest(
            @ApiParam(
                    name = "guest entity",
                    type = "GuestDTO",
                    value = "Model of guest that will be deleted",
                    required = true)
            @RequestBody GuestDTO guestDTO) {

        guestService.deleteGuest(guestDTO);
        return ResponseEntity.ok("Guest deleted!");
    }

    @ApiOperation(value = "Find guest by id", notes = "This method get a guest")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @GetMapping("/byId/{id}")
    public ResponseEntity<GuestDTO> findGuestById(
            @ApiParam(
                    name = "id",
                    type = "Long",
                    value = "Guest Id",
                    example = "1",
                    required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(guestService.findById(id));
    }

    @ApiOperation(value = "Find guest by name", notes = "This method get guest(s) by name")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @GetMapping("/byName/{name}")
    public ResponseEntity<List<GuestDTO>> findGuestByName(
            @ApiParam(
                    name = "name",
                    type = "String",
                    value = "Guest name",
                    example = "Roger Federer",
                    required = true)
            @PathVariable String name) {
        return ResponseEntity.ok(guestService.findByName(name));
    }

    @ApiOperation(value = "Find all guests", notes = "This method get all guests")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<GuestDTO>> findAll() {
        return ResponseEntity.ok(guestService.findAll());
    }
}
