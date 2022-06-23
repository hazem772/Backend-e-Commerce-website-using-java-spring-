package com.hazem.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hazem.DTO.ProductDto;
import com.hazem.model.Product;
import com.hazem.model.User;
import com.hazem.model.WishList;
import com.hazem.repository.WishListRepository;

@Service 
public class WishListService {

	@Autowired
	WishListRepository wishlistRepository;
	
	@Autowired
    ProductService productService;

	public void createWishlist(WishList wishList) {
        wishlistRepository.save(wishList);
    }
	
	 public List<ProductDto> getWishListForUser(User user) {
	        final List<WishList> wishLists = wishlistRepository.findAllByUserOrderByCreatedDateDesc(user);
	        List<ProductDto> productDtos = new ArrayList<>();
	        for (WishList wishList: wishLists) {
	            productDtos.add(productService.getProductDto(wishList.getProduct()));
	        }

	        return productDtos;
	    }
	 public boolean product_exist_in_wishlist(User user , Product product) 
	 {
		 final List<WishList> wishLists = wishlistRepository.findAllByUserOrderByCreatedDateDesc(user);
		 for (WishList wishList: wishLists) {
	            if(wishList.getId()==product.getId()) return true;
	        }
		 return false;
	 }
}
