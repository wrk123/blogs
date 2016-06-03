package com.blogspot.app.controller;

import java.util.List;
import java.util.Date;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.blogspot.app.model.User;
import com.blogspot.app.model.SessionToken;
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
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	/* User related operations */
	
	//Method for creating users 
	@RequestMapping(value="/user",method=RequestMethod.POST)
	public @ResponseBody ResponseEntity<User> createUser(@RequestBody User userDetails) throws Exception{		
		Optional<User> users=null;
		User user=null;
		SessionToken userSession=null;		
		
		//for checking update user 
		users=userDAO.findOneById(userDetails.getId());
		if (!users.isPresent()){
			
			//checking for unique email 
			users=userDAO.findByEmail(userDetails.getEmail());
			if(users.isPresent()){
				return  new ResponseEntity<User>(user, HttpStatus.CONFLICT);
			}
			//insert new user 
			try{
				user=new User(userDetails.getEmail().toLowerCase(),userDetails.getName(),userDetails.getContact(), new Date(),new Date(),userDetails.getPassword(),0);				
				userSession=new SessionToken(user.getId());
				
				userDAO.save(user);
				userSession.setUserId(user.getId());
				authTokenDAO.save(userSession);
				
				}catch(Exception e){
					throw new Exception("Exception in saving user details...",e);
				}
		}else{  //for updating an existing user
				
				if(checkUserAuth(user))
					return new ResponseEntity<User>(HttpStatus.UNAUTHORIZED);
				
				userDetails.setLastModifiedTime(new Date());
				userDAO.save(userDetails);
			}		
			return  new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	
	//for fetching user details of a single user 
	@RequestMapping(value="/user/{userId}",method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<User> getOneUserDetails(@PathVariable Long userId){
		Optional<User> user=null;
		user=userDAO.findOneById(userId);
		if(!user.isPresent()){
				return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
			}
		
			/*if(checkUserAuth(user))
				return new ResponseEntity<User>(HttpStatus.UNAUTHORIZED);				
		*/
		
		return new ResponseEntity<User>(user.get(), HttpStatus.OK);
	}
		
	//user filter for likes 
	@RequestMapping(value="/user/{userId}/blogpost/like",method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<Blog>> getBlogLikes(@PathVariable Long userId){
		Optional<User> user=null;
		List<Blog> blogs=null;
	
		user=userDAO.findOneById(userId);
		if(!user.isPresent()){
			return new ResponseEntity<List<Blog>>(HttpStatus.NOT_FOUND);
		}
		if(checkUserAuth(user.get()))
			return new ResponseEntity<List<Blog>>(HttpStatus.UNAUTHORIZED);
		
		blogs=blogRepo.findByUserIdOrderByBlogLikesDesc(user.get().getId());
		
		for(Blog blog: blogs)												//get the comments for each blog
		{	blog.setReview(reviewRepo.findByBlogId(blog.getBlogId()));
			blog.setUser(user.get());
		}
		return new ResponseEntity<List<Blog>>(blogs, HttpStatus.OK);
	}
	
	//user filter for dislikes 
	@RequestMapping(value="/user/{userId}/blogpost/dislike",method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<Blog>> getBlogDislikes(@PathVariable Long userId) {
	Optional<User> user=null;
	
	List<Blog> blogs=null;	
	user=userDAO.findOneById(userId);
		if(!user.isPresent()){
			return new ResponseEntity<List<Blog>>(HttpStatus.NOT_FOUND);
		}
		if(checkUserAuth(user.get()))
			return new ResponseEntity<List<Blog>>(HttpStatus.UNAUTHORIZED);
		
		blogs=blogRepo.findByUserIdOrderByBlogDislikesDesc(user.get().getId());
		for(Blog blog: blogs)												//get the comments for each blog
		{	blog.setReview(reviewRepo.findByBlogId(blog.getBlogId()));
			blog.setUser(user.get());
		}	
		return new ResponseEntity<List<Blog>>(blogs, HttpStatus.OK);
	}
	
	//user filter for comments  
	@RequestMapping(value="/user/{userId}/blogpost/comment",method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<Blog>> getBlogComments(@PathVariable Long userId){
		Optional<User> user = null;
		List<Blog> blogs = null;
        
		user=userDAO.findOneById(userId);
		if (!user.isPresent()) {
			return new ResponseEntity<List<Blog>>(HttpStatus.NOT_FOUND);
		}
		if (checkUserAuth(user.get()))
			return new ResponseEntity<List<Blog>>(HttpStatus.UNAUTHORIZED);

		blogs = blogRepo.findByUserId(user.get().getId());
			    
		for (Blog blog : blogs) // get the comments for each blog
			blog.setReview(reviewRepo.findByBlogIdOrderByCreationTimeDesc(blog.getBlogId()));
		
		return new ResponseEntity<List<Blog>>(blogs, HttpStatus.OK);
	}
	
	//user filter for month based blogposts
	@RequestMapping(value="/user/{userId}/blogpost/month",method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<Blog>> getMonthlyBlog(@PathVariable Long userId){
		Optional<User> user=null;
		List<Blog> blogs=null;	
		user=userDAO.findOneById(userId);
		
		if(!user.isPresent()){
			return new ResponseEntity<List<Blog>>(HttpStatus.NOT_FOUND);
		}
		if(checkUserAuth(user.get()))
			return new ResponseEntity<List<Blog>>(HttpStatus.UNAUTHORIZED);
		
		blogs=blogRepo.findByUserIdOrderByCreationTimeMonth(user.get().getId());
		
		for(Blog blog: blogs)												//get the comments for each blog
		{		blog.setReview(reviewRepo.findByBlogIdOrderByCreationTimeDesc(blog.getBlogId()));
				blog.setUser(user.get());
		}		
		return new ResponseEntity<List<Blog>>(blogs, HttpStatus.OK);
	}
	
	//user filter for year based blogposts
	@RequestMapping(value="/user/{userId}/blogpost/year",method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<Blog>> getYearlyBlog(@PathVariable Long userId){
		Optional<User> user=null;
		List<Blog> blogs=null;	
		user=userDAO.findOneById(userId);
		if(!user.isPresent()){
			return new ResponseEntity<List<Blog>>(HttpStatus.NOT_FOUND);
		}
		if(checkUserAuth(user.get()))
			return new ResponseEntity<List<Blog>>(HttpStatus.UNAUTHORIZED);
		
		blogs=blogRepo.findByUserIdOrderByCreationTime(user.get().getId());
		
		for(Blog blog: blogs)												//get the comments for each blog
		{	blog.setReview(reviewRepo.findByBlogIdOrderByCreationTimeDesc(blog.getBlogId()));
			blog.setUser(user.get());
		}
		return new ResponseEntity<List<Blog>>(blogs, HttpStatus.OK);
	}
	
	//checking whether user has logged in or not 
	boolean checkUserAuth(User user){
		SessionToken userSession=null;		
		userSession=authTokenDAO.findByUserId(user.getId());
			if(userSession.getAuthToken()!=null)
				return false;
			else 
				return true;
	}
	
}
