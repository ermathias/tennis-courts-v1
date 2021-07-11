package com.tenniscourts.controller;

import com.tenniscourts.config.BaseRestController;
import com.tenniscourts.dto.GuestDTO;
import com.tenniscourts.dto.ReservationDTO;
import com.tenniscourts.service.AdminService;
import com.tenniscourts.service.GuestService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AdminController  extends BaseRestController {

    private final AdminService adminService;

    public AdminController(final AdminService adminService){
        this.adminService = adminService;
    }


    @ApiOperation("Close past reservations at the end of the day")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "All reservation past current time has been put in CLOSED status"),
            @ApiResponse(code = 400, message = "Bad request. Check your input"),
            @ApiResponse(code = 500, message = "An error has occurred during closing the reservations")
    })
    @RequestMapping(value = "/api/admin/close-daily-reservations", method = RequestMethod.GET)
    public ResponseEntity<String> closeReservations() {
        adminService.closeDailyReservations();
        return new ResponseEntity<>(
                "Daily reservations closing service has run successfully.",
                HttpStatus.OK);
    }

    @ApiOperation("Get history reservation")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "A list with the past reservations has returned successfully"),
            @ApiResponse(code = 400, message = "Bad request. Check your input"),
            @ApiResponse(code = 500, message = "An error has occurred during getting reservations history")
    })
    @RequestMapping(value = "/api/admin/get-history", method = RequestMethod.GET)
    public ResponseEntity<List<ReservationDTO>> getHistoryReservations() {
        return ResponseEntity.ok(adminService.getHistory());
    }

}
