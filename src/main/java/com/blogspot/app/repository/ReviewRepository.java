package com.blogspot.app.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.blogspot.app.model.Blog;
import com.blogspot.app.model.Review;

public interface ReviewRepository extends CrudRepository<Review, Long>{

	List<Review> findByBlogId(Long blogId);
	
	List<Review> findByBlogIdOrderByCreationTimeDesc(Long blogId);

}
