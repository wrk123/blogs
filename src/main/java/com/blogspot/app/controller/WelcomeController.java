package com.blogspot.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WelcomeController {


	@RequestMapping(value="/")
	public  ModelAndView welcome(){

    	ModelAndView model = new ModelAndView("/jsp/index.jsp");
    	return model;
	}
	
}
