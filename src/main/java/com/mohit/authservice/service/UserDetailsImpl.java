package com.mohit.authservice.service;

import com.mohit.authservice.Exception.UserNotFound;
import com.mohit.authservice.entity.User;
import com.mohit.authservice.userrepository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsImpl implements UserDetailsService {
    @Autowired private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user= userRepository.findByUsername(username).orElseThrow(()-> new UserNotFound("user not found"));
       return new CustomUserDetails(user);
    }

}
