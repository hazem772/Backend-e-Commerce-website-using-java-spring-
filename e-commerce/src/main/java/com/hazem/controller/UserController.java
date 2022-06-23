package com.hazem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hazem.DTO.ResponseDto;
import com.hazem.DTO.SignInDto;
import com.hazem.DTO.SignInReponseDto;
import com.hazem.DTO.SignupDto;
import com.hazem.service.UserService;

@RestController
@RequestMapping("user")
public class UserController {
	
	 @Autowired
     UserService userService;
	
	 @PostMapping("/signup")
	    public ResponseDto signup(@RequestBody SignupDto signupDto) {
	        return userService.signUp(signupDto);
	    }

	 
	 @PostMapping("/signin")
	    public SignInReponseDto signIn(@RequestBody SignInDto signInDto) {
	        return userService.signIn(signInDto);
	    }
}
