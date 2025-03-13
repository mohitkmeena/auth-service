package com.mohit.authservice.controller;

import com.mohit.authservice.dto.ResetPasswordDto;
import com.mohit.authservice.dto.VerifyOtp;
import com.mohit.authservice.service.ForgetPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/password")
public class ForgetPasswordController {
    @Autowired
    private ForgetPasswordService forgetPasswordSecvice;

    @PostMapping("/verify")
    public String verifyEmail(@RequestParam String email){
        return forgetPasswordSecvice.verifyEmailAndSendOtp(email);
    }
    @PostMapping("/verify-otp")
    public String veriifyOtp(@RequestBody VerifyOtp verifyOtp ){

        return  forgetPasswordSecvice.verifyOtp(verifyOtp.getOtp(), verifyOtp.getEmail());
    }
    @PutMapping("/update-password")

    public String updatePassword(@RequestBody ResetPasswordDto resetPasswordDto){

        return  forgetPasswordSecvice.updatePassword(resetPasswordDto.getEmail(),resetPasswordDto.getPassword(), resetPasswordDto.getRpassword());
    }

}
