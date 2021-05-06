package com.tenniscourts.guests;

import com.tenniscourts.config.BaseRestController;
import com.tenniscourts.tenniscourts.ApiOperation;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.PathParam;
import java.util.List;

@RestController
@RequestMapping("/guest")
@AllArgsConstructor
public class GuestController extends BaseRestController {
	
    @Autowired
    private final GuestService guestService;

    @ApiOperation(Value='create_Guest')
    @PostMapping("/create")
    public ResponseEntity<Void> createGuest(@RequestBody GuestDTO guestDTO) {
        return ResponseEntity.created(locationByEntity(guestService.createGuest(guestDTO).getId())).build();
    }

    @ApiOperation(Value='guest_by_id',response = GuestDTO.class, tags = "getById")
    @GetMapping("/get/{id}")
    public ResponseEntity<GuestDTO> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(guestService.guestById(id));
    }

    @ApiOperation(Value='guest_by_name',response = GuestDTO.class, tags = "getByName")
    @GetMapping("/get/{name}")
    public ResponseEntity<List<GuestDTO>> getByName(@PathParam("name") String name) {
        return ResponseEntity.ok(guestService.guestByName(name));
    }

    @ApiOperation(Value='guest_List',response = GuestDTO.class, tags = "guestList")
    @GetMapping("/getList")
    public ResponseEntity<List<GuestDTOs>> getListAll() {
        return ResponseEntity.ok(guestService.guestList());
    }

    @ApiOperation(Value='update_guest_by_id')
    @PutMapping("/update/{id}")
    public ResponseEntity<Void> updateGuest(@PathVariable("id") Long id, @RequestBody GuestDTO guestDTO) {
        return ResponseEntity.created(locationByEntity(guestService.updateGuest(id, guestDTO).getId())).build();
    }


    @ApiOperation(Value='delete_guest_by_id')
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteGuest(@PathVariable("id") Long id) {
        guestService.deleteGuest(id);
        HttpHeaders responseHeaders = new HttpHeaders();
        return new ResponseEntity<String>("Guest deleted.", responseHeaders, HttpStatus.NO_CONTENT);
    }
}