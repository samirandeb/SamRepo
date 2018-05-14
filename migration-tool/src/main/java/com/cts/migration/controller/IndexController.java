package com.cts.migration.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

	@RequestMapping("/")
    public String homePage(Model model) {
		model.addAttribute("currentModule","home");
        model.addAttribute("fragmentName", "fragments/home");
        return "index";
    }
}
