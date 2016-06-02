package com.blogspot.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import com.blogspot.app.model.Review;

public interface ReviewRepository extends CrudRepository<Review, Long>,Repository<Review, Long>{
	
	Optional<Review> findOneByBlogId(Long blogId);
	
	List<Review> findByBlogId(Long blogId);
	
	List<Review> findByBlogIdOrderByCreationTimeDesc(Long blogId);

}
