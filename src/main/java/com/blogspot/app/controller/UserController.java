package com.blogspot.app.controller;

import java.util.List;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.blogspot.app.model.Blog;
import com.blogspot.app.model.SessionToken;
import com.blogspot.app.model.User;
import com.blogspot.app.repository.BlogRepository;
import com.blogspot.app.repository.ReviewRepository;
import com.blogspot.app.repository.SessionTokenRepository;
import com.blogspot.app.repository.UserRepository;



@RestController
public class UserController {

	@Autowired
	UserRepository userDAO;
	
	@Autowired
	SessionTokenRepository authTokenDAO;
	
	@Autowired
	BlogRepository blogRepo;
	
	@Autowired
	ReviewRepository reviewRepo;
	
	
	@RequestMapping(value="/",method=RequestMethod.GET)
	public @ResponseBody String welcome(){
		return "Welcome to  the blogspot.";
	}
	
	
	/* User related operations */
	
	//Method for creating users 
	@RequestMapping(value="/user",method=RequestMethod.POST)
	public @ResponseBody ResponseEntity<User> createUser(@RequestBody User userDetails) throws Exception{
		User user=null;
		SessionToken userSession=null;		
		
		//for checking existing email 
		user=userDAO.findByEmail(userDetails.getEmail());
		if(user!=null){
			return new ResponseEntity<User>(HttpStatus.CONFLICT);
		}
		//for checking update user 
		user=userDAO.findOne(userDetails.getId());
		if (user==null){
			//insert new user 
			try{
				user=new User(userDetails.getEmail().toLowerCase(),userDetails.getName(),userDetails.getContact(), new Date(),new Date(),userDetails.getPassword(),0);				
				userSession=new SessionToken(user.getId());
				
				userDAO.save(user);
				authTokenDAO.save(userSession);
				
				}catch(Exception e){
					throw new Exception("Exception in saving user details...",e);
				}
		}else{  //for updating an existing user
				
				if(!checkUserAuth(user))
					return new ResponseEntity<User>(HttpStatus.UNAUTHORIZED);
				
				userDetails.setLastModifiedTime(new Date());
				userDAO.save(userDetails);
			}		
			return  new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	
	//for fetching user details of a single user 
	@RequestMapping(value="/user/{userId}",method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<User> getOneUserDetails(@PathVariable Long userId)throws Exception{
		User user=null;
			user=userDAO.findOne(userId);
			if(user==null){
				return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
			}
			System.out.println("user value :::"+user);
			
				if(!checkUserAuth(user))
					return new ResponseEntity<User>(HttpStatus.UNAUTHORIZED);				
			System.out.println(" >>>> fetching user details and displaying the same.");
			
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
		
	//user filter for likes 
	@RequestMapping(value="/user/{userId}/blogpost/like",method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<Blog>> getBlogLikes(@PathVariable Long userId)throws Exception{
	User user=null;

	List<Blog> blogs=null;	
	user=userDAO.findOne(userId);
		if(user==null){
			return new ResponseEntity<List<Blog>>(HttpStatus.NOT_FOUND);
		}
		if(!checkUserAuth(user))
			return new ResponseEntity<List<Blog>>(HttpStatus.UNAUTHORIZED);
		
		blogs=blogRepo.findByUserIdOrderByBlogLikesDesc(user.getId());
		
		for(Blog blog: blogs)												//get the comments for each blog
		{	blog.setReview(reviewRepo.findByBlogId(blog.getBlogId()));
			blog.setUser(user);
		}
		return new ResponseEntity<List<Blog>>(blogs, HttpStatus.OK);
	}
	
	//user filter for dislikes 
	@RequestMapping(value="/user/{userId}/blogpost/dislike",method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<Blog>> getBlogDislikes(@PathVariable Long userId)throws Exception{
	User user=null;
	
	List<Blog> blogs=null;	
	user=userDAO.findOne(userId);
		if(user==null){
			return new ResponseEntity<List<Blog>>(HttpStatus.NOT_FOUND);
		}
		if(!checkUserAuth(user))
			return new ResponseEntity<List<Blog>>(HttpStatus.UNAUTHORIZED);
		
		blogs=blogRepo.findByUserIdOrderByBlogDislikesDesc(user.getId());
		for(Blog blog: blogs)												//get the comments for each blog
		{	blog.setReview(reviewRepo.findByBlogId(blog.getBlogId()));
			blog.setUser(user);
		}	
		return new ResponseEntity<List<Blog>>(blogs, HttpStatus.OK);
	}
	
	//user filter for comments  
	@RequestMapping(value="/user/{userId}/blogpost/comment",method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<Blog>> getBlogComments(@PathVariable Long userId)throws Exception{
		User user = null;

		List<Blog> blogs = null;

		user = userDAO.findOne(userId);
		if (user == null) {
			return new ResponseEntity<List<Blog>>(HttpStatus.NOT_FOUND);
		}
		if (!checkUserAuth(user))
			return new ResponseEntity<List<Blog>>(HttpStatus.UNAUTHORIZED);

		blogs = blogRepo.findByUserId(user.getId());
		
		System.out.println(">>>>>>>>>> Before printing blog values :::::"+blogs.toString());
		
		//System.out.println("::Displaying the value of the query ....."+reviewRepo.findByBlogIdOrderByCreationTimeDesc("1"));
		
		for (Blog blog : blogs) // get the comments for each blog
		{
			blog.setReview(reviewRepo.findByBlogIdOrderByCreationTimeDesc(blog.getBlogId()));
			System.out.println(">>>>>>>> After setting review value :::::["+reviewRepo.findByBlogIdOrderByCreationTimeDesc(blog.getBlogId())+"]");
		}
		System.out.println(":: Blog value is ::{"+blogs+"} ");
		return new ResponseEntity<List<Blog>>(blogs, HttpStatus.OK);
	}
	
	//user filter for month based blogposts
	@RequestMapping(value="/user/{userId}/blogpost/month",method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<Blog>> getMonthlyBlog(@PathVariable Long userId)throws Exception{
	User user=null;
	
	List<Blog> blogs=null;	
	user=userDAO.findOne(userId);
		if(user==null){
			return new ResponseEntity<List<Blog>>(HttpStatus.NOT_FOUND);
		}
		if(!checkUserAuth(user))
			return new ResponseEntity<List<Blog>>(HttpStatus.UNAUTHORIZED);
		
		blogs=blogRepo.findByUserIdOrderByCreationTimeMonth(user.getId());
		
		for(Blog blog: blogs)												//get the comments for each blog
		{		blog.setReview(reviewRepo.findByBlogIdOrderByCreationTimeDesc(blog.getBlogId()));
				blog.setUser(user);
		}		
		return new ResponseEntity<List<Blog>>(blogs, HttpStatus.OK);
	}
	
	//user filter for month based blogposts
	@RequestMapping(value="/user/{userId}/blogpost/year",method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<Blog>> getYearlyBlog(@PathVariable Long userId)throws Exception{
	User user=null;
	
	List<Blog> blogs=null;	
	user=userDAO.findOne(userId);
		if(user==null){
			return new ResponseEntity<List<Blog>>(HttpStatus.NOT_FOUND);
		}
		if(!checkUserAuth(user))
			return new ResponseEntity<List<Blog>>(HttpStatus.UNAUTHORIZED);
		
		blogs=blogRepo.findByUserIdOrderByCreationTime(user.getId());
		
		for(Blog blog: blogs)												//get the comments for each blog
		{	blog.setReview(reviewRepo.findByBlogIdOrderByCreationTimeDesc(blog.getBlogId()));
			blog.setUser(user);
		}
		return new ResponseEntity<List<Blog>>(blogs, HttpStatus.OK);
	}
	
	
	//checking whether user has logged in or not 
	boolean checkUserAuth(User user){
		SessionToken userSession=null;		
		userSession=authTokenDAO.findByUserId(user.getId());
		System.out.println("Fetching user session value ::"+userSession);		
				if(userSession.getAuthToken()!=null)
					return true;
				else 
					return false;
	}
	
}