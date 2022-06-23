package com.hazem.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hazem.exceptions.AuthenticationFailException;
import com.hazem.model.AuthenticationToken;
import com.hazem.model.User;
import com.hazem.repository.TokenRepository;

@Service
public class AuthenticationService {
	@Autowired
    TokenRepository tokenRepository;

    public void saveConfirmationToken(AuthenticationToken authenticationToken) {
        tokenRepository.save(authenticationToken);
    }

    public AuthenticationToken getToken(User user) {
        return tokenRepository.findByUser(user);
    }
    
    public User getUser(String token) {
        final AuthenticationToken authenticationToken = tokenRepository.findByToken(token);
        if(Objects.isNull(authenticationToken)) return null;
       
        return authenticationToken.getUser();
    }
    
    
    public void authenticate(String token) throws AuthenticationFailException {
        if(Objects.isNull(token)) {
            throw new AuthenticationFailException("token not valid");
        }
        if(Objects.isNull(getUser(token))) {
            throw new AuthenticationFailException("token not present");
        }
    }
    
    
}
