package com.mohit.authservice.dto;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class SignInRequest {
    private String username;
    private String password;
    private String name;
}
