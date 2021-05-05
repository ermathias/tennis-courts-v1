package com.tenniscourts.tenniscourts;

import com.tenniscourts.config.BaseRestController;


import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@AllArgsConstructor
public class TennisCourtController extends BaseRestController {

	@Autowired
    private final TennisCourtService tennisCourtService;

    //TODO: implement rest and swagger
    @RequestMapping(value="/addTennisCourt",method = RequestMethod.POST)
    public ResponseEntity<Void> addTennisCourt(TennisCourtDTO tennisCourtDTO) {
        return ResponseEntity.created(locationByEntity(tennisCourtService.addTennisCourt(tennisCourtDTO).getId())).build();
    }

    //TODO: implement rest and swagger
    @RequestMapping(value="/findTennisCourtById",method = RequestMethod.GET,path = "{tennisCourtId}")
    public ResponseEntity<TennisCourtDTO> findTennisCourtById(Long tennisCourtId) {
    	
        return ResponseEntity.ok(tennisCourtService.findTennisCourtById(tennisCourtId));
    }

    @RequestMapping(value="/findTennisCourtByName",method = RequestMethod.GET,path = "{name}")
    public TennisCourtDTO findTennisCourtByName(String name) {
    	
        return tennisCourtService.findTennisCourtByName(name);
    }
    
    @RequestMapping(value="/getallCourts",method = RequestMethod.GET)
    public TennisCourtDTO getallCourts() {
    	
        return tennisCourtService.getallCourts();
    }
    
    @DeleteMapping(path = "{tennisCourtId}")
	public String deleteProduct(@PathVariable("tennisCourtId") Long tennisCourtId) {
    	tennisCourtService.deleteTennisCourtById(tennisCourtId);
		return "deleted the court";
	}
    
    @PutMapping
	public String updateProduct(@RequestBody TennisCourtDTO tennisCourtDTO) {
    	tennisCourtService.updatecourt(tennisCourtDTO);
		return "Updated the court";
	}
    
    
    
        //TODO: implement rest and swagger
    public ResponseEntity<TennisCourtDTO> findTennisCourtWithSchedulesById(Long tennisCourtId) {
        return ResponseEntity.ok(tennisCourtService.findTennisCourtWithSchedulesById(tennisCourtId));
    }
}
