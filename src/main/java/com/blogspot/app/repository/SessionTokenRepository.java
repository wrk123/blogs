package com.blogspot.app.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.blogspot.app.model.Blog;
import com.blogspot.app.model.SessionToken;
import com.blogspot.app.model.User;

public interface SessionTokenRepository extends CrudRepository<SessionToken, Long> {

	SessionToken findByUserId(Long userId);
}
