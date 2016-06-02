package com.blogspot.app.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;

import com.blogspot.app.model.Blog;
import com.blogspot.app.model.User;

@org.springframework.stereotype.Repository
public interface UserRepository extends Repository<User,Long>,CrudRepository<User, Long>{
	
	Optional<User> findByEmail(String email);
	
	User findById(Long userId);
	
}
