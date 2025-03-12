package com.mohit.authservice.controller;

import com.mohit.authservice.dto.LoginRequest;
import com.mohit.authservice.dto.SignInRequest;
import com.mohit.authservice.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {
    @Autowired private AuthService authService;

    @PostMapping("/signup")
    public  String signUp(@RequestBody SignInRequest signInRequest){
         boolean isSignedUp=authService.isAlreadyUser(signInRequest.getUsername());
         if(isSignedUp){
             return "Username already registered";
         }
         authService.signup(signInRequest);
         return "Sign up successful";
    }

    @PostMapping("/login")
    public  String login(@RequestBody LoginRequest loginRequest){

       return authService.login(loginRequest);
    }

    @GetMapping("/ping")
    public ResponseEntity<String> ping(){
        System.out.println("in ping ");
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        if(auth!=null&&auth.isAuthenticated()){
            return ResponseEntity.ok(auth.getName());
        }
        return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("unauthorized");
    }

}
