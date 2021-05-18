package com.tenniscourts.guests;

import com.tenniscourts.config.BaseRestController;
import com.tenniscourts.schedules.ScheduleDTO;
import com.tenniscourts.tenniscourts.TennisCourtDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

    @Api(value = "GuestController", description = "REST APIs for guest users")
    @AllArgsConstructor
    @RestController
    public class GuestController  extends BaseRestController {

        private final GuestService guestService;

        @ApiOperation(value = "Creates new User", response = Void.class)
        @PostMapping("/user")
        public ResponseEntity<Void> addGuestUser(GuestUserDTO guestUserDTO) {
            return ResponseEntity.created(locationByEntity(guestService.addUpdateUser(guestUserDTO).getId())).build();
        }

        @ApiOperation(value = "Deletes User by ID", response = GuestUserDTO.class)
        @DeleteMapping("/user/{id}")
        public ResponseEntity<GuestUserDTO> deleteUser(@PathVariable Long id) {
            return ResponseEntity.ok(guestService.deleteUser(id));
        }


        @ApiOperation(value = "Finds User by ID", response = GuestUserDTO.class)
        @GetMapping("/user/{id}")
        public ResponseEntity<GuestUserDTO> findById(@PathVariable Long id) {
            return ResponseEntity.ok(guestService.findUser(id));
        }


        @ApiOperation(value = "Finds User by Name", response = GuestUserDTO.class)
        @GetMapping("/user/name/{name}")
        public  ResponseEntity<List<GuestUserDTO>>  findByName(@PathVariable String name) {
            return ResponseEntity.ok(guestService.findUserByName(name));
        }


        @ApiOperation("Show all Guests")
        @GetMapping
        public ResponseEntity<List<Guest>> showGuestsList() {
            return ResponseEntity.ok(guestService.findAllGuests());
        }

}


