package com.sam.app;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {
	
	@RequestMapping(path="/ping",produces="application/json")
	public String ping() {
		return "active";
	}

}
