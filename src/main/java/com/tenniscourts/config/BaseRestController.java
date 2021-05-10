package com.tenniscourts.config;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import springfox.documentation.annotations.ApiIgnore;

import java.net.URI;


@RestController
@RequestMapping("/Tennis")
public class BaseRestController {

  protected URI locationByEntity(Long entityId){
        return ServletUriComponentsBuilder.fromCurrentRequest().path(
                "/{id}").buildAndExpand(entityId).toUri();
    }
  
  @RequestMapping(value="/hello", method = RequestMethod.GET)
	public String hello()
	{
		return "Hello";
	}
}
