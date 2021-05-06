package com.tenniscourts.tenniscourts;

import com.tenniscourts.config.BaseRestController;
import org.springframework.http.ResponseEntity;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(path ="tennisCourt/")
public class TennisCourtController extends BaseRestController {

	@Autowired
    TennisCourtService tennisCourtService;
	
	@Autowired
	guestService guestService;
 
	@ApiOperation(Value='add_court', tags = "addTennisCourt")
	@PostMapping(path ="/addCourt")
    public ResponseEntity<Void> addTennisCourt(@Valid @RequestBody TennisCourtDTO tennisCourtDTO) {
        return ResponseEntity.created(locationByEntity(tennisCourtService.addTennisCourt(tennisCourtDTO).getId())).build();
    }

	@ApiOperation(Value='find_court',response = TennisCourtDTO.class, tags = "findTennisCourtById")
    @GetMapping("/findTennisCourt/{tennisCourtId}")
    public ResponseEntity<TennisCourtDTO> findTennisCourtById(@PathVariable("tennisCourtId") Long tennisCourtId) {
        return ResponseEntity.ok(tennisCourtService.findTennisCourtById(tennisCourtId));
    }

	@ApiOperation(Value='find_court_schedules',response = TennisCourtDTO.class, tags = "findTennisCourtWithSchedulesById")
    @GetMapping("/findTennisCourtSchedules/{tennisCourtId}")
    public ResponseEntity<TennisCourtDTO> findTennisCourtWithSchedulesById(@PathVariable("tennisCourtId") Long tennisCourtId) {
        return ResponseEntity.ok(tennisCourtService.findTennisCourtWithSchedulesById(tennisCourtId));
    }
    
	@ApiOperation(Value='update_court_details',description ="updating tennis Court details")
    @PutMapping("/updateCourt")
    public ResponseEntity<Void> updateTennisCourt(@Valid @RequestBody TennisCourtDTO tennisCourtDTO) {
        return ResponseEntity.created(locationByEntity(tennisCourtService.updateTennisCourt(tennisCourtDTO).getId())).build();
    }  
    
	@ApiOperation(Value='delete_court_details',description ="Deleting court by tennis Court Id")
    @deleteMapping("/deleteCourt/{tennisCourtId}")
    public ResponseEntity<Void> deleteTennisCourt(@PathVariable("tennisCourtId") Long tennisCourtId) {
        return ResponseEntity.ok(tennisCourtService.deleteTennisCourt(tennisCourtId));
    }
    
	@ApiOperation(Value='guest_List',response = TennisCourtDTO.class, tags = "guestList")
    @GetMapping("/guestList/{tennisCourtId}")
    public ResponseEntity<List<GuestDTOs>> guestList(@PathVariable("tennisCourtId") Long tennisCourtId) {
        return ResponseEntity.ok(guestService.guestList());
    }
    
}
