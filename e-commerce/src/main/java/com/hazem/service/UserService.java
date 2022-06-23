package com.hazem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hazem.DTO.ResponseDto;
import com.hazem.DTO.SignInDto;
import com.hazem.DTO.SignInReponseDto;
import com.hazem.DTO.SignupDto;
import com.hazem.exceptions.AuthenticationFailException;
import com.hazem.exceptions.CustomException;
import com.hazem.model.AuthenticationToken;
import com.hazem.model.User;
import com.hazem.repository.UserRepository;

import javax.transaction.Transactional;
import javax.xml.bind.DatatypeConverter;
import javax.xml.datatype.DatatypeConstants;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;


@Service
public class UserService {

	@Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationService authenticationService;

	public ResponseDto signUp(SignupDto signupDto) {
        if (Objects.nonNull(userRepository.findByEmail(signupDto.getEmail()))) {
            throw new CustomException("user already present");
        }
        String encryptedpassword = signupDto.getPassword();

        try {
            encryptedpassword = hashPassword(signupDto.getPassword());
        }   catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        User user = new User(signupDto.getFirstname(), signupDto.getLastname(),
                signupDto.getEmail(), encryptedpassword);

        userRepository.save(user);

        final AuthenticationToken authenticationToken = new AuthenticationToken(user);

        authenticationService.saveConfirmationToken(authenticationToken);

        ResponseDto responseDto = new ResponseDto("success", "user created succesfully");
        return responseDto;
    }

	private String hashPassword(String password) throws NoSuchAlgorithmException  {
		MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        String hash = DatatypeConverter.printHexBinary(digest).toUpperCase();
        return hash;
	}	
	
	 public SignInReponseDto signIn(SignInDto signInDto) {
	      
	        User user = userRepository.findByEmail(signInDto.getEmail());

	        if (Objects.isNull(user)) {
	            throw new AuthenticationFailException("user is not valid");
	        }

	     
	        try {
	            if (!user.getPasswoprd().equals(hashPassword(signInDto.getPassword()))) {
	                throw new AuthenticationFailException("wrong password");
	            }
	        }   catch (NoSuchAlgorithmException e) {
	            e.printStackTrace();
	        }

	        
	        AuthenticationToken token = authenticationService.getToken(user);
	        if (Objects.isNull(token)) {
	            throw new CustomException("token is not present");
	        }

	        
	        return new SignInReponseDto("sucess", token.getToken());

	 }
}
