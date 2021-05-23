package com.tenniscourts.guests;

import com.tenniscourts.config.BaseRestController;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
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

    @ApiOperation("Saves a guest to the database")
    @PostMapping("/add")
    public ResponseEntity<Void> save(@Valid @RequestBody GuestDTO guestDTO ) {
        return ResponseEntity.created(locationByEntity(guestService.save(guestDTO).getId())).build();
    }

    @DeleteMapping
    public void delete(Long guestId){
        guestService.delete(guestId);
    }

}
