package com.hazem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hazem.model.User;
import com.hazem.model.WishList;

@Repository
public interface WishListRepository extends JpaRepository<WishList, Integer> {
	  List<WishList> findAllByUserOrderByCreatedDateDesc(User user);
}
