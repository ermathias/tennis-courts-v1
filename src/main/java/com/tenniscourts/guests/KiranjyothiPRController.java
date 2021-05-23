package com.tenniscourts.guests;

import javax.validation.Valid;
import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tenniscourts.config.BaseRestController;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/guests")
public class KiranjyothiPRController extends BaseRestController {

    private final KiranjyothiPRService guestService;

    @PostMapping
    public ResponseEntity<Void> addGuest(@RequestBody @Valid KiranjyothiPRDTO guestDTO) {
        return ResponseEntity.created(locationByEntity(guestService.addGuest(guestDTO).getId())).build();
    }

    @PutMapping(value = "/{guestId}")
    public ResponseEntity<KiranjyothiPRDTO> update(@RequestBody @Valid KiranjyothiPRDTO guestDTO) {
        return ResponseEntity.ok(guestService.update(guestDTO));
    }

    @GetMapping(value = "/{guestId}")
    public ResponseEntity<KiranjyothiPRDTO> findById(@PathVariable("guestId") long id) {
        return ResponseEntity.ok(guestService.getById(id));
    }

    @GetMapping
    public ResponseEntity<KiranjyothiPRDTO> findByName(@RequestParam(value="name") String name) {
        return ResponseEntity.ok(guestService.getByName(name));
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<KiranjyothiPRDTO>> findAll() {
        return ResponseEntity.ok(guestService.getAll());
    }

    @DeleteMapping(value = "/{guestId}")
    public ResponseEntity<KiranjyothiPRDTO> deleteById(@PathVariable("guestId") long id) {
        return ResponseEntity.ok(guestService.deleteById(id));
    }
}
