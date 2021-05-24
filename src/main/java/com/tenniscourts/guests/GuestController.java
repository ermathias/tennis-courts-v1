package com.tenniscourts.guests;

import com.tenniscourts.config.BaseRestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@Api(value = "API Guests")
@RequestMapping("api/guests")
public class GuestController extends BaseRestController {

    @Autowired
    private final GuestService guestService;

    @ApiOperation("Fetch all the guests")
    @GetMapping
    public ResponseEntity<List<GuestDTO>> getAll(){
        return ResponseEntity.ok(guestService.getAll());
    }

    @ApiOperation("Fetch guests by name")
    @GetMapping(path = "name/{name}")
    public ResponseEntity<List<GuestDTO>> findByName(@PathVariable String name) {
        return ResponseEntity.ok(guestService.findByNameContaining(name));
    }

    @ApiOperation("Fetch guest by id")
    @GetMapping(path = "id/{id}")
    public ResponseEntity<GuestDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(guestService.findById(id));
    }

    /**
     *
     * @param guestDTO with just name attribute to save and name plus id to update
     * @return Status code 201 when saves a guest and 200 when update
     * @throws -EntityNotFoundException when guest id is not found in database
     */
    @ApiOperation("Saves or update a guest to the database")
    @PostMapping("/add")
    public ResponseEntity<Void> save(@Valid @RequestBody GuestDTO guestDTO ) throws EntityNotFoundException {
        return ResponseEntity.created(locationByEntity(guestService.save(guestDTO).getId())).build();
    }

    @ApiOperation("Delete a guest by id")
    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable("id") Long guestId){
        guestService.delete(guestId);
    }

}
