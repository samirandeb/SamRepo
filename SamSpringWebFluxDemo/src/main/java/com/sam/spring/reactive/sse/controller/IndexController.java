package com.sam.spring.reactive.sse.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
	
	@RequestMapping({"/","/stockdash"})
	public String stockdash() {
		return "stockdashboard";
	}
	
}
