package com.mohit.authservice.service;

import com.mohit.authservice.dto.LoginRequest;
import com.mohit.authservice.dto.SignInRequest;
import com.mohit.authservice.entity.User;
import com.mohit.authservice.userrepository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
@Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;

    private BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder(12);
    public boolean isAlreadyUser(String username) {
        return userRepository.existsByUsername(username);
    }

    public void signup(SignInRequest signInRequest) {
        signInRequest.setPassword(bCryptPasswordEncoder.encode(signInRequest.getPassword()));
        User user= User.builder()
                        .username(signInRequest.getUsername())
                        .password(signInRequest.getPassword())
                       .name(signInRequest.getName())
                       .build();
        userRepository.save(user);
    }
    public String login(LoginRequest loginRequest){

        try{
            Authentication auth=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword()));

            String  token =jwtService.generateToken(auth.getName());

            return token;
        }
        catch (BadCredentialsException ex){
            System.out.println("message " +ex.getMessage());
            return "invalid credentials";
        }
    }
}
