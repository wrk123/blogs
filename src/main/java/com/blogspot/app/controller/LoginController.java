package com.blogspot.app.controller;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.blogspot.app.model.SessionToken;
import com.blogspot.app.model.User;
import com.blogspot.app.repository.SessionTokenRepository;
import com.blogspot.app.repository.UserRepository;

@RestController
@RequestMapping(value="/auth")
public class LoginController {


	@Autowired
	UserRepository userDAO;
	
	@Autowired
	SessionTokenRepository authTokenDAO;
	
	//private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	private ResponseEntity<User> loginUser(@RequestBody User userLogin)throws Exception{
		User user=null;
		user=userDAO.findByEmail(userLogin.getEmail());
		if(user==null){
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
		
		if(!user.getPassword().equals(userLogin.getPassword())){
			return new ResponseEntity<User>(HttpStatus.UNAUTHORIZED);
		}
		
		
		SessionToken userSession=null;
		userSession=authTokenDAO.findByUserId(user.getId());
		
	
		if(userSession.getAuthToken()!=null){
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}
	
		validateToken(userSession,user);
			return new ResponseEntity<User>(user,HttpStatus.OK);		
	
	}
	
	@RequestMapping(value="/logout",method=RequestMethod.POST)
	String userLogOut(@RequestBody User userLogout){
		User user=null;
		SessionToken userSession=null;
		user=userDAO.findByEmail(userLogout.getEmail());
		if(user==null){
			return "User with email "+userLogout.getEmail()+" is NOT_FOUND";
		} 
		userSession=authTokenDAO.findByUserId(user.getId());
		if(userSession.getAuthToken()==null){
			return "You have already logged out.";
		}
		invalidateToken(userSession,user);
			
		
		return "You are successfully logged out."; 
	}
	
	void invalidateToken(SessionToken userSession,User user){
		userSession.setAuthToken(null);
		try{  authTokenDAO.save(userSession);	
			}
		catch(Exception e) {   e.printStackTrace();  }
	} 
	
	void validateToken(SessionToken userSession,User user){
		userSession.setAuthToken(user.hashCode());		
		userSession.setLastModifiedTime(new Date());
		try{		authTokenDAO.save(userSession);
			}
		catch(Exception e){    
			System.out.println("Exception occured while logging in ::::");
			e.printStackTrace();  }
		
	}
	
}

