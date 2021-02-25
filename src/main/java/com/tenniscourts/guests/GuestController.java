package com.tenniscourts.guests;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import com.tenniscourts.config.BaseRestController;
import lombok.AllArgsConstructor;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

@AllArgsConstructor
@RestController
@RequestMapping("/api/guests")
@Api(description = "Guests REST API")
@CrossOrigin
public class GuestController extends BaseRestController {

    @Inject
    private final GuestService guestService;

    @PostMapping
    @ApiOperation(value = "Create a new guest.")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "The guest has been created.")
    })
    public ResponseEntity<Void> create(@RequestBody GuestDTO guestDTO) {
        return ResponseEntity.created(locationByEntity(guestService.insert(guestDTO).getId())).build();
    }

    @PutMapping
    @ApiOperation(value = "Update a guest.")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "The guest has been updated."),
        @ApiResponse(code = 404, message = "The informed guest was not found.")
    })
    public ResponseEntity<Void> update(@RequestBody GuestDTO guestDTO) {
        return ResponseEntity.created(locationByEntity(guestService.update(guestDTO).getId())).build();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete a guest by its id.")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "The guest has been deleted."),
        @ApiResponse(code = 404, message = "The informed guest was not found.")
    })
    public void delete(@PathVariable("id") Long id) {
        guestService.delete(id);
    }

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "Find a guest by its id.")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "The guest object."),
        @ApiResponse(code = 404, message = "The informed guest was not found.")
    })
    public ResponseEntity<GuestDTO> findById(@PathVariable("id") Long guestId) {
        return ResponseEntity.ok(guestService.findGuest(guestId));
    }

    @GetMapping("/name")
    @ApiOperation("Find guests by a given name.")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "A list of guests filtered by the given name.")
    })
    public ResponseEntity<List<GuestDTO>> findByName(@RequestParam("name") String name,
            @RequestParam("pageNumber") Optional<Integer> pageNumber,
            @RequestParam("pageSize") Optional<Integer> pageSize,
            @RequestParam("orderBy") Optional<String> orderBy) {
        return ResponseEntity.ok(guestService.findByName(name, createPageable(pageNumber, pageSize, orderBy)));
    }

    @GetMapping
    @ApiOperation("List all the guests.")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "A list containing all the guests.")
    })
    public ResponseEntity<List<GuestDTO>> findAll(@RequestParam("pageNumber") Optional<Integer> pageNumber,
            @RequestParam("pageSize") Optional<Integer> pageSize,
            @RequestParam("orderBy") Optional<String> orderBy) {
        return ResponseEntity.ok(guestService.findAll(createPageable(pageNumber, pageSize, orderBy)));
    }

    private Pageable createPageable(Optional<Integer> pageNumber, Optional<Integer> pageSize, Optional<String> orderBy) {
        return PageRequest.of(pageNumber.orElse(0), pageSize.orElse(10), Sort.by(orderBy.orElse("id")).ascending());
    }
}
