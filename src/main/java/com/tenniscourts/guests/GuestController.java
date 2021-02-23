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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@AllArgsConstructor
@RestController
@RequestMapping("/api/guests")
@Api(description = "Guests REST API")
@CrossOrigin(origins = "*")
public class GuestController extends BaseRestController {

    @Inject
    private final GuestService guestService;

    @PostMapping(value = "/")
    @ApiOperation(value = "Create a new guest.")
    public ResponseEntity<Void> create(GuestDTO guestDTO) {
        return ResponseEntity.created(locationByEntity(guestService.save(guestDTO).getId())).build();
    }

    @PutMapping(value = "/")
    @ApiOperation(value = "Update a guest.")
    public ResponseEntity<Void> update(GuestDTO guestDTO) {
        return ResponseEntity.created(locationByEntity(guestService.save(guestDTO).getId())).build();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete a guest by its id.")
    public void delete(@PathVariable("id") Long id) {
        guestService.delete(id);
    }

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "Find a guest by its id.")
    public ResponseEntity<GuestDTO> findById(@PathVariable("id") Long guestId) {
        return ResponseEntity.ok(guestService.findGuest(guestId));
    }

    @GetMapping("/name/{name}")
    @ApiOperation("Find guests by a given name.")
    public ResponseEntity<List<GuestDTO>> findByName(@RequestParam("name") String name,
            @RequestParam("pageNumber") Optional<Integer> pageNumber, 
            @RequestParam("pageSize") Optional<Integer> pageSize,
            @RequestParam("orderBy") Optional<String> orderBy) {
        return ResponseEntity.ok(guestService.findByName(name, createPageable(pageNumber, pageSize, orderBy)));
    }

    @GetMapping("/")
    @ApiOperation("List all the guests.")
    public ResponseEntity<List<GuestDTO>> findAll(@RequestParam("pageNumber") Optional<Integer> pageNumber,
            @RequestParam("pageSize") Optional<Integer> pageSize,
            @RequestParam("orderBy") Optional<String> orderBy) {
        return ResponseEntity.ok(guestService.findAll(createPageable(pageNumber, pageSize, orderBy)));
    }

    private Pageable createPageable(Optional<Integer> pageNumber, Optional<Integer> pageSize, Optional<String> orderBy) {
        return PageRequest.of(pageNumber.orElse(0), pageSize.orElse(10), Sort.by(orderBy.orElse("id")).ascending());
    }
}
