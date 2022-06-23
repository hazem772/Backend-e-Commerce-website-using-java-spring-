package com.hazem.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hazem.DTO.AddToCartDto;
import com.hazem.DTO.CartDto;
import com.hazem.common.ApiResponse;
import com.hazem.model.User;
import com.hazem.service.AuthenticationService;
import com.hazem.service.CartService;
import com.hazem.service.ProductService;

@RestController
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private CartService cartservice;
	
	@Autowired
	AuthenticationService authenticationService;
	
	
	@PostMapping("/add")
	public ResponseEntity<ApiResponse> addToCart
    	(@RequestBody AddToCartDto addtocartdto,@RequestParam("token") String token)
		{
		  authenticationService.authenticate(token);
		  User user = authenticationService.getUser(token);
		  
		  
		  cartservice.addToCart(addtocartdto , user);
		  return new ResponseEntity<>(new ApiResponse(true, "Added to cart"), HttpStatus.CREATED);
		}
	
	
    @GetMapping("/")
    public ResponseEntity<CartDto> getCartItems(@RequestParam("token") String token) {
        authenticationService.authenticate(token);
        User user = authenticationService.getUser(token);

        CartDto cartDto = cartservice.listCartItems(user);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }


    @DeleteMapping("/delete/{cartItemId}")
    public ResponseEntity<ApiResponse> deleteCartItem
    (@PathVariable("cartItemId") Integer itemId, @RequestParam("token") String token) {
        authenticationService.authenticate(token);      
        User user = authenticationService.getUser(token);

        cartservice.deleteCartItem(itemId, user);

        return new ResponseEntity<>(new ApiResponse(true, "Item has been removed"), HttpStatus.OK);

    }
}
