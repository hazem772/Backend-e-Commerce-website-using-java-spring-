package com.hazem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hazem.DTO.ProductDto;
import com.hazem.common.ApiResponse;
import com.hazem.model.Product;
import com.hazem.model.User;
import com.hazem.model.WishList;
import com.hazem.service.AuthenticationService;
import com.hazem.service.WishListService;

@RestController
@RequestMapping("/wishlist")
public class WishListController {
 
	@Autowired
	WishListService wishListService;
	
	@Autowired
	AuthenticationService authenticationService;
	 
	@PostMapping("/add")
	public ResponseEntity<ApiResponse> addToWishList(@RequestBody Product product, @RequestParam("token") String token) 
	{
		 authenticationService.authenticate(token);
		 
		 User user = authenticationService.getUser(token);
		 
		 WishList wishList = new WishList(user , product);
		 
		 if(wishListService.product_exist_in_wishlist(user , product)) {
			 return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Product already exists in wish list "), HttpStatus.NOT_FOUND);
		 }
		 
		 wishListService.createWishlist(wishList);
		 ApiResponse apiResponse = new ApiResponse(true, "Added to wishlist");
	     return  new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
	}
	
	 @GetMapping("/{token}")
	 public ResponseEntity<List<ProductDto>> getWishList(@PathVariable("token") String token) {

	        
	        authenticationService.authenticate(token);

	        User user = authenticationService.getUser(token);

	        List<ProductDto> productDtos = wishListService.getWishListForUser(user);

	        return new ResponseEntity<>(productDtos, HttpStatus.OK);

	    }

}
