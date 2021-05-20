package com.tenniscourts.tenniscourts;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@Api(value = "", tags = "Tennis Courts", description = "Tennis-Courts REST Endpoints")
@RequestMapping(value = "/tennis-courts")
@Validated
public interface TennisCourtAPIService {

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Adds tennis court", nickname = "addTennisCourt")
    default ResponseEntity<Void> addTennisCourt(
            @ApiParam(value = "Tennis Court data") @Valid @RequestBody TennisCourtDTO tennisCourtDTO) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @GetMapping(value = "/{tennisCourtId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Returns tennis court given an id", nickname = "addTennisCourt")
    default ResponseEntity<TennisCourtDTO> findTennisCourtById(
            @PathVariable Long tennisCourtId) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @GetMapping(value = "/{tennisCourtId}/schedules", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Returns tennis court given an id with schedules", nickname = "findTennisCourtWithSchedulesById")
    default ResponseEntity<TennisCourtDTO> findTennisCourtWithSchedulesById(
            @PathVariable Long tennisCourtId) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

}
