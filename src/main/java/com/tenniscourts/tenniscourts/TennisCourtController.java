package com.tenniscourts.tenniscourts;

import com.tenniscourts.config.BaseRestController;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping( "/tennis-courts" )
public class TennisCourtController extends BaseRestController {

  @Autowired
  private final TennisCourtService tennisCourtService;

  @PostMapping
  @ApiOperation( "Creates a new tennis court" )
  @ApiResponses( value = { @ApiResponse( code = 201, message = "Tennis court created with success" ) } )
  public ResponseEntity< Void > addTennisCourt( @Valid @RequestBody TennisCourtDTO tennisCourtDTO ) {
    return ResponseEntity
            .created( locationByEntity( tennisCourtService.addTennisCourt( tennisCourtDTO ).getId() ) )
            .build();
  }

  @GetMapping( "/{id}" )
  @ApiOperation( "Find Tennis court by id" )
  @ApiResponses( value = { @ApiResponse( code = 200, message = "Ok" ) } )
  public ResponseEntity< TennisCourtDTO > findTennisCourtById( @PathVariable( "id" ) Long tennisCourtId ) {
    return ResponseEntity.ok( tennisCourtService.findTennisCourtById( tennisCourtId ) );
  }

  @GetMapping( "with-schedules/{id}" )
  @ApiOperation( "Find Tennis court by id" )
  @ApiResponses( value = { @ApiResponse( code = 200, message = "Ok" ) } )
  public ResponseEntity< TennisCourtDTO > findTennisCourtWithSchedulesById( @PathVariable( "id" ) Long tennisCourtId ) {
    return ResponseEntity.ok( tennisCourtService.findTennisCourtWithSchedulesById( tennisCourtId ) );
  }
}
