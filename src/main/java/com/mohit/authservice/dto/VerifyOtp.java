package com.mohit.authservice.dto;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class VerifyOtp {
    private String email;
    private Integer otp;
}
