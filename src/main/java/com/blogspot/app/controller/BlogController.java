package com.blogspot.app.controller;


import java.util.Date;
import java.util.List;
import java.util.Optional;

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
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;


@RestController
@PropertySource("classpath:ResourceBundle.properties")
public class BlogController {

	@Autowired
	Environment env;
	
	@Autowired
	UserRepository userDAO;
	
	@Autowired
	BlogRepository blogRepo;
	
	@Autowired
	SessionTokenRepository authTokenDAO;

	@Autowired
	ReviewRepository reviewRepo;
	
//	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	//for fetching all blogs on like of a comment
	@RequestMapping(value="/home",method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<Blog>> fetchAllblogs(){
		
		List<Blog> blogs=(List<Blog>) blogRepo.findByIsActiveOrderByCreationTimeDesc(true);
			
			return new ResponseEntity<List<Blog>>(blogs,HttpStatus.OK);
	}
	
	//for posting a new blog
	@RequestMapping(value="/blogpost",method=RequestMethod.POST)
	public @ResponseBody ResponseEntity<Blog> createBlog(@RequestBody Blog blogDetails){
		Optional<User> user=null;
		
		Blog blogs=null;
		Date publishDate=null;
		user=userDAO.findOneById(blogDetails.getUserId());	
		//check for authorization
		if(checkUserAuth(user.get()))
			return new ResponseEntity<Blog>(HttpStatus.UNAUTHORIZED);
		try{
			//in case of new blog 
			if(blogDetails.getBlogId()==null){
				 if(!blogDetails.getDraft())
					   publishDate =new Date();
				 else 		
					 blogDetails.setIsActive(false);
					
				    blogs=new Blog(blogDetails.getBlogTitle(),blogDetails.getBlogContent(),blogDetails.getUserId(),blogDetails.getDraft(),new Date(),publishDate,blogDetails.getBlogLikes(),blogDetails.getBlogDislikes(),blogDetails.getIsActive());
				    blogs.setUser(user.get());
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
					blogDetails.setUser(user.get());
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
		Optional<Blog> blogs=null;
		Optional<User> user=null;
		user=userDAO.findOneById(userId);
		if(checkUserAuth(user.get()))
			return new ResponseEntity<Blog>(HttpStatus.UNAUTHORIZED);
	
		blogs=blogRepo.findOneByBlogId(blogId);
		if(!blogs.isPresent()){
			return new ResponseEntity<Blog>(HttpStatus.NOT_FOUND);
		}
		
		Review comment=null;
		if(user.get().getCredit()< Integer.parseInt(env.getProperty("credit.min")))
			return new ResponseEntity<Blog>(HttpStatus.PRECONDITION_FAILED);
		
			//for updating credit of the user who created the  blog
		user=userDAO.findOneById(blogs.get().getUserId());
		
		int score=user.get().getCredit()+Integer.parseInt(env.getProperty("credit.comment"));				// add the credit 
		user.get().setCredit(score);
		userDAO.save(user.get());
															//save the comments
		comment=new Review(review.getComments(),new Date(),userId, blogId);
		reviewRepo.save(comment); 
	
		return new ResponseEntity<Blog>(blogs.get(),HttpStatus.OK);
	}
	
	//to like a blog
	@RequestMapping(value="/user/{userId}/blogpost/{blogId}/like",method=RequestMethod.DELETE)
	public @ResponseBody ResponseEntity<Blog> likeBlog(@PathVariable(value="userId") Long userId,@PathVariable(value="blogId") Long blogId)	throws Exception{
		Optional<Blog> blogs=null;
		Optional<User> user=null;
		
		user=userDAO.findOneById(userId);
		if(checkUserAuth(user.get()))
			return new ResponseEntity<Blog>(HttpStatus.UNAUTHORIZED);
		
		blogs=blogRepo.findOneByBlogId(blogId);
		if(!blogs.isPresent()){
			return new ResponseEntity<Blog>(HttpStatus.NOT_FOUND);
		}
					
		if(user.get().getCredit()< Integer.parseInt(env.getProperty("credit.min")))
			return new ResponseEntity<Blog>(HttpStatus.PRECONDITION_FAILED);
		
		//for updating credit of the user who created the  blog
		user=userDAO.findOneById(blogs.get().getUserId());
		int score=user.get().getCredit()+Integer.parseInt(env.getProperty("credit.like")) ;				// add the credit 
		user.get().setCredit(score);
		userDAO.save(user.get());
		
		int  likes=blogs.get().getBlogLikes()+1;
		blogs.get().setBlogLikes(likes);
		blogRepo.save(blogs.get());
		
		return new ResponseEntity<Blog>(blogs.get(),HttpStatus.OK);
	}
	
	//to dislike a blog
	@RequestMapping(value="/user/{userId}/blogpost/{blogId}/dislike",method=RequestMethod.DELETE)
	public @ResponseBody ResponseEntity<Blog> dislikeBlog(@PathVariable(value="userId") Long userId,@PathVariable(value="blogId") Long blogId){
		Optional<Blog> blogs=null;
		Optional<User> user=null;
		
		user=userDAO.findOneById(userId);
		if(checkUserAuth(user.get()))
			return new ResponseEntity<Blog>(HttpStatus.UNAUTHORIZED);
		
		blogs=blogRepo.findOneByBlogId(blogId);
		if(blogs==null){
			return new ResponseEntity<Blog>(HttpStatus.NOT_FOUND);
		}
			
		if(user.get().getCredit()<Integer.parseInt(env.getProperty("credit.min")))
			return new ResponseEntity<Blog>(HttpStatus.PRECONDITION_FAILED);
		
		
		//for updating credit of the user who created the  blog
		user=userDAO.findOneById(blogs.get().getUserId());
		int score=user.get().getCredit()-Integer.parseInt(env.getProperty("credit.dislike"));				// subtract the credit 
		user.get().setCredit(score);
		userDAO.save(user.get());
		
		int  dislikes=blogs.get().getBlogDislikes()+1;  //increase the number of likes 
		blogs.get().setBlogDislikes(dislikes);
		blogRepo.save(blogs.get());
		
		return new ResponseEntity<Blog>(blogs.get(),HttpStatus.OK);
	}
	
	
	//for making post inActive by the user
	@RequestMapping(value="/user/{userId}/blogpost/{userBlogId}/isActive",method=RequestMethod.DELETE)
	public @ResponseBody ResponseEntity<Blog> markBlogInactive(@PathVariable Long userId, @PathVariable Long userBlogId){
		Optional<Blog> blogs=null;
		Optional<User> user=null;
		user=userDAO.findOneById(userId);
		
		if(checkUserAuth(user.get()))
			return new ResponseEntity<Blog>(HttpStatus.UNAUTHORIZED);
		
		blogs=blogRepo.findOneByBlogId(userBlogId);
		if(!blogs.isPresent())
			return new ResponseEntity<Blog>(HttpStatus.NOT_FOUND);
		
		
		if(blogs.get().getUserId()==user.get().getId())
		{	if(blogs.get().getIsActive())
				blogs.get().setIsActive(false);
			else
				{	if(blogs.get().getPublishTime()==null)
					{	blogs.get().setPublishTime(new Date());		// in case of making a blog active, one need to publish it 
						blogs.get().setDraft(false);
						blogs.get().setIsActive(true); }
					else
						blogs.get().setIsActive(true); 
				}
		}
		else 
			return new ResponseEntity<Blog>(HttpStatus.PRECONDITION_FAILED);	
			
		blogRepo.save(blogs.get());
		
		return new  ResponseEntity<Blog>(blogs.get(),HttpStatus.OK); 
	}
	
	//fetch all the blogs of a specific user
	@RequestMapping(value="/user/{userId}/blogpost",method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<Blog>> getOneUsersAllBlogs(@PathVariable Long userId){
		Optional<User> user=null;
		List<Blog> blogs=null;
		user=userDAO.findOneById(userId);
		
		if(checkUserAuth(user.get()))
			return new ResponseEntity<List<Blog>>(HttpStatus.UNAUTHORIZED);
		
		blogs=blogRepo.findAllByUserIdOrderByCreationTimeDesc(user.get().getId());
		if(blogs == null && blogs.isEmpty())
			return new ResponseEntity<List<Blog>>(HttpStatus.NO_CONTENT);
		
		return new  ResponseEntity<List<Blog>>(blogs,HttpStatus.OK);
	}
	
	//fetch one blog details
	@RequestMapping(value="/blog/{id}",method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<Blog> fetchOneBlog(@PathVariable Long id){
		Optional<Blog> blog=null;
		blog=blogRepo.findOneByBlogId(id);
		
		return new  ResponseEntity<Blog>(blog.get(),HttpStatus.OK);
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
