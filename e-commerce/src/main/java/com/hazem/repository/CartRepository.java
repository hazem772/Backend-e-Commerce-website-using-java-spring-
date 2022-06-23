package com.hazem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hazem.model.Cart;
import com.hazem.model.User;

public interface CartRepository extends JpaRepository<Cart, Integer> {
	 List<Cart> findAllByUserOrderByCreatedDateDesc(User user);
}
