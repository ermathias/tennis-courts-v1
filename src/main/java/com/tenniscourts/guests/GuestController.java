package com.tenniscourts.guests;

import com.tenniscourts.config.BaseRestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/guests")
public class GuestController extends BaseRestController {
}
