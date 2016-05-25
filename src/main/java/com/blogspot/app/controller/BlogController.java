package com.blogspot.app.controller;


import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blogspot.app.model.User;
import com.blogspot.app.model.Blog;
import com.blogspot.app.model.Review;
import com.blogspot.app.model.SessionToken;
import com.blogspot.app.repository.UserRepository;
import com.blogspot.app.repository.BlogRepository;
import com.blogspot.app.repository.ReviewRepository;
import com.blogspot.app.repository.SessionTokenRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;


@RestController
public class BlogController {

	@Autowired
	UserRepository userDAO;
	
	@Autowired
	BlogRepository blogRepo;
	
	@Autowired
	SessionTokenRepository authTokenDAO;

	@Autowired
	ReviewRepository reviewRepo;
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	//for fetching all blogs on like of a comment
	@RequestMapping(value="/home",method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<Blog>> fetchAllblogs(){
		
		List<Blog> blogs=(List<Blog>) blogRepo.findByIsActiveOrderByCreationTimeDesc(true);
			
			return new ResponseEntity<List<Blog>>(blogs,HttpStatus.OK);
	}
	
	//for posting a new blog
	@RequestMapping(value="/blogpost",method=RequestMethod.POST)
	public @ResponseBody ResponseEntity<Blog> createBlog(@RequestBody Blog blogDetails){
		User user=null;
		
		Blog blogs=null;
		Date publishDate=null;
		user=userDAO.findById(blogDetails.getUserId());	
		//check for authorization
		if(!checkUserAuth(user))
			return new ResponseEntity<Blog>(HttpStatus.UNAUTHORIZED);
		try{
			//in case of new blog 
			if(blogDetails.getBlogId()==null){
				 if(!blogDetails.getDraft())
					   publishDate =new Date();
				 else 		
					 blogDetails.setIsActive(false);
					
				    blogs=new Blog(blogDetails.getBlogTitle(),blogDetails.getBlogContent(),blogDetails.getUserId(),blogDetails.getDraft(),new Date(),publishDate,blogDetails.getBlogLikes(),blogDetails.getBlogDislikes(),blogDetails.getIsActive());
				    blogs.setUser(user);
				    blogRepo.save(blogs);
				}else
				{	//for existing blog
					blogs=blogRepo.findOne(blogDetails.getBlogId());
					if(blogs==null){
						return new ResponseEntity<Blog>(HttpStatus.NOT_FOUND);
					}
					if(!blogDetails.getDraft())
						publishDate =new Date();
					else	
						blogDetails.setIsActive(false);
					
									
					blogDetails.setPublishTime(publishDate);
					blogDetails.setUser(user);
					blogRepo.save(blogDetails);
				}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return new ResponseEntity<Blog>(blogs,HttpStatus.OK);
	}
	
	//for posting comment on a blog
	
	@RequestMapping(value="/user/{userId}/blogpost/{blogId}/comment",method=RequestMethod.POST)
	public @ResponseBody ResponseEntity<Blog> commentBlog(@PathVariable(value="userId") Long userId,@PathVariable(value="blogId") Long blogId,@RequestBody Review review)	throws Exception{
		Blog blogs=null;
		User user=null;
		user=userDAO.findById(userId);
		if(!checkUserAuth(user))
			return new ResponseEntity<Blog>(HttpStatus.UNAUTHORIZED);
	
		blogs=blogRepo.findOne(blogId);
		if(blogs==null){
			return new ResponseEntity<Blog>(HttpStatus.NOT_FOUND);
		}
		
		Review comment=null;
		if(user.getCredit()<50)
			return new ResponseEntity<Blog>(HttpStatus.PRECONDITION_FAILED);
		
			//for updating credit of the user who created the  blog
		user=userDAO.findById(blogs.getUserId());
		
		int score=user.getCredit()+15;				// add the credit 
		user.setCredit(score);
		userDAO.save(user);
															//save the comments
		comment=new Review(review.getComments(),new Date(),user.getId(), blogId);
		reviewRepo.save(comment); 
	
		return new ResponseEntity<Blog>(blogs,HttpStatus.OK);
	}
	
	//to like a blog
	@RequestMapping(value="/user/{userId}/blogpost/{blogId}/like",method=RequestMethod.DELETE)
	public @ResponseBody ResponseEntity<Blog> likeBlog(@PathVariable(value="userId") Long userId,@PathVariable(value="blogId") Long blogId)	throws Exception{
		Blog blogs=null;
		User user=null;
		user=userDAO.findById(userId);
		if(!checkUserAuth(user))
			return new ResponseEntity<Blog>(HttpStatus.UNAUTHORIZED);
		
		blogs=blogRepo.findOne(blogId);
		if(blogs==null){
			return new ResponseEntity<Blog>(HttpStatus.NOT_FOUND);
		}
					
		if(user.getCredit()<50)
			return new ResponseEntity<Blog>(HttpStatus.PRECONDITION_FAILED);
		
		//for updating credit of the user who created the  blog
		user=userDAO.findById(blogs.getUserId());
		int score=user.getCredit()+5;				// add the credit 
		user.setCredit(score);
		userDAO.save(user);
		
		int  likes=blogs.getBlogLikes()+1;
		blogs.setBlogLikes(likes);
		blogRepo.save(blogs);
		
		return new ResponseEntity<Blog>(blogs,HttpStatus.OK);
	}
	
	//to dislike a blog
	@RequestMapping(value="/user/{userId}/blogpost/{blogId}/dislike",method=RequestMethod.DELETE)
	public @ResponseBody ResponseEntity<Blog> dislikeBlog(@PathVariable(value="userId") Long userId,@PathVariable(value="blogId") Long blogId){
		Blog blogs=null;
		User user=null;
		user=userDAO.findById(userId);
		if(!checkUserAuth(user))
			return new ResponseEntity<Blog>(HttpStatus.UNAUTHORIZED);
		
		blogs=blogRepo.findOne(blogId);
		if(blogs==null){
			return new ResponseEntity<Blog>(HttpStatus.NOT_FOUND);
		}
			
		if(user.getCredit()<50)
			return new ResponseEntity<Blog>(HttpStatus.PRECONDITION_FAILED);
		
		
		//for updating credit of the user who created the  blog
		user=userDAO.findById(blogs.getUserId());
		int score=user.getCredit()-1;				// subtract the credit 
		user.setCredit(score);
		userDAO.save(user);
		
		int  dislikes=blogs.getBlogDislikes()+1;  //increase the number of likes 
		blogs.setBlogDislikes(dislikes);
		blogRepo.save(blogs);
		
		return new ResponseEntity<Blog>(blogs,HttpStatus.OK);
	}
	
	//checking user logged in 
	boolean checkUserAuth(User user){
		SessionToken userSession=null;
			userSession=authTokenDAO.findByUserId(user.getId());
			if(userSession.getAuthToken()!=null)
				return true;
			else return false;
	}
	
	//for making post inActive by the user
	@RequestMapping(value="/user/{userId}/blogpost/{userBlogId}/isActive",method=RequestMethod.DELETE)
	public @ResponseBody ResponseEntity<Blog> markBlogInactive(@PathVariable Long userId, @PathVariable Long userBlogId){
		User user=null;
		Blog blogs = null;
		user=userDAO.findById(userId);
		
		if(!checkUserAuth(user))
			return new ResponseEntity<Blog>(HttpStatus.UNAUTHORIZED);
		
		blogs=blogRepo.findOne(userBlogId);
		if(blogs==null)
			return new ResponseEntity<Blog>(HttpStatus.NOT_FOUND);
		
		
		if(blogs.getUserId()==user.getId())
		{	if(blogs.getIsActive())
				blogs.setIsActive(false);
			else
				blogs.setIsActive(true);
		}
		else 
			return new ResponseEntity<Blog>(HttpStatus.PRECONDITION_FAILED);
			
		blogRepo.save(blogs);
		
		return new  ResponseEntity<Blog>(blogs,HttpStatus.OK); 
	}
	
	//fetch all the blogs of a specific user
	@RequestMapping(value="/user/{userId}/blogpost",method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<Blog>> getOneUsersAllBlogs(@PathVariable Long userId){
		User user=null;
		List<Blog> blogs=null;
		user=userDAO.findById(userId);
		
		if(!checkUserAuth(user))
			return new ResponseEntity<List<Blog>>(HttpStatus.UNAUTHORIZED);
		
		blogs=blogRepo.findAllByUserIdOrderByCreationTimeDesc(user.getId());
		if(blogs == null)
			return new ResponseEntity<List<Blog>>(HttpStatus.NO_CONTENT);
		
		return new  ResponseEntity<List<Blog>>(blogs,HttpStatus.OK);
	}
}
