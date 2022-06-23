package com.hazem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hazem.model.AuthenticationToken;
import com.hazem.model.User;

public interface TokenRepository extends JpaRepository<AuthenticationToken, Integer> {
	AuthenticationToken findByUser(User user);
	AuthenticationToken findByToken(String token);
}
