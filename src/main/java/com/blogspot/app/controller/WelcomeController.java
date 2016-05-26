package com.blogspot.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.blogspot.app.model.SessionToken;
import com.blogspot.app.model.User;
import com.blogspot.app.repository.SessionTokenRepository;
import com.blogspot.app.repository.UserRepository;

@Controller
public class WelcomeController {

	
	@Autowired
	SessionTokenRepository authTokenDAO;

	@Autowired
	UserRepository userDAO;
	
	@RequestMapping(value="/")
	public  ModelAndView welcome(){

    	ModelAndView model = new ModelAndView("/jsp/index.jsp");
    	return model;
	}
	
	//for authenticating user 
	@RequestMapping(value="/profile/{id}")
	public  ModelAndView userHome(@PathVariable Long id){
		//fetch the token for this id from the database and authenticate it by un-hashing the token and verifying the emai id entered,
		User user=null;
		user=userDAO.findById(id);
		System.out.println(user);
		ModelAndView model=null;
		
		SessionToken session=session=authTokenDAO.findByUserId(id);
		if(session.getAuthToken()!=null){
			if(user.hashCode()== session.getAuthToken())
			{ 
				model = new ModelAndView("/jsp/main.jsp");
				model.addObject("user",user);
			}
			else 
				model = new ModelAndView("/jsp/error.jsp");
		}
		
		return model;
	}
}
