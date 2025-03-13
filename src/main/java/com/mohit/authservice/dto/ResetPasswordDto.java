package com.mohit.authservice.dto;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class ResetPasswordDto {
    private String email,password,rpassword;
}
