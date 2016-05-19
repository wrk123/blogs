package com.blogspot.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.blogspot.app.model.Blog;
import com.blogspot.app.model.User;

public interface BlogRepository extends CrudRepository<Blog, Long>{

	List<Blog> findByUserId(Long userId);
	
	List<Blog> findAllByUserIdOrderByCreationTimeDesc(Long userId);
	
	List<Blog> findByUserIdOrderByCreationTimeDesc(Long userId);
	
	List<Blog> findByUserIdOrderByBlogLikesDesc(Long userId);
	
	List<Blog> findByUserIdOrderByBlogDislikesDesc(Long userId);
	
	@Query("SELECT m FROM Blog m where m.userId=:userId order by  MONTH(m.creationTime) desc")
	List<Blog> findByUserIdOrderByCreationTimeMonth(@Param("userId") Long userId);
	
	@Query("SELECT y FROM Blog y where y.userId=:userId order by  YEAR(y.creationTime) desc")
	List<Blog> findByUserIdOrderByCreationTime(@Param("userId") Long userId);
		 
}
