package com.mohit.authservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/v1/ping")
public class PingController {
    @GetMapping("/ping")
    public ResponseEntity<String> ping(){

        Authentication auth= SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.isAuthenticated() ) {
            return ResponseEntity.ok("Authenticated User: " + auth.getName());
        }
        return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("unauthorized");
    }
}
