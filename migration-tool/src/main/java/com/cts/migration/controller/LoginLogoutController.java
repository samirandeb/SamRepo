package com.cts.migration.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginLogoutController {
	
	
	@Value("${spring.application.name}")
	private String appName;
	
	@Value("${info.version}")
	private String appVersion;
	
	/**
     * Open login page
     *
     * @return String
     */
    @RequestMapping(value= "/login", method = RequestMethod.GET)
    public String getLoginPage() {
    	
        return "login";
    }

    /**
     * Open login page
     *
     * @return String
     */
    @RequestMapping(value= "/logout", method = RequestMethod.GET)
    public String getLogoutPage() {
        return "redirect:/login";
    }
    
    @ModelAttribute("appVersion")
    public String appVersion() {
      return appVersion;
    }
    
    @ModelAttribute("appName")
    public String appName() {
      return appName;
    }

}
