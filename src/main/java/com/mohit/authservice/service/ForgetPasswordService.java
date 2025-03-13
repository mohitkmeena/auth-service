package com.mohit.authservice.service;

import com.mohit.authservice.Exception.ForgetPasswordReqException;
import com.mohit.authservice.Exception.UserNotFound;
import com.mohit.authservice.dto.EmailDto;
import com.mohit.authservice.entity.ForgetPassword;
import com.mohit.authservice.entity.User;
import com.mohit.authservice.userrepository.ForgetPasswordRepository;
import com.mohit.authservice.userrepository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

@Service
public class ForgetPasswordService {
    @Autowired
    private UserRepository userRepository;
    @Autowired private MailService emailService;
    @Autowired private ForgetPasswordRepository forgetPasswordRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    public String verifyEmailAndSendOtp(String email){
        User user=userRepository.findByEmail(email).orElseThrow(()->new UserNotFound("enter correct email"));
        Integer otp=otpGeneration();
        EmailDto mailBody=EmailDto.builder()
                .to(email)
                .sub("otp verification")
                .message("otp to reset the password "+otp)
                .build();

        ForgetPassword forgetPassword=ForgetPassword.builder()
                .user(user)
                .otp(otp)
                .expirationTime(new Date(System.currentTimeMillis()+10*60*1000))
                .build();
        emailService.sendMail(mailBody);

        forgetPasswordRepository.save(forgetPassword);
        return "email sent successfully";
    }

    private Integer otpGeneration(){
        Random random=new Random();
        return random.nextInt(000_000, 999_999);
    }

    public String verifyOtp(Integer otp,String email){
        User user=userRepository.findByEmail(email).orElseThrow(()->new UserNotFound("user not found"));
        ForgetPassword forgetPassword=forgetPasswordRepository.findByOtpAndUser(otp, user).orElseThrow(()-> new ForgetPasswordReqException("request not found"));
        if(forgetPassword.getExpirationTime().before(Date.from(Instant.now()))){

            forgetPasswordRepository.delete(forgetPassword);
            return "Otp expired";
        }
        return "OTP VERIFIED";
    }
    @Transactional
    public String updatePassword(String email,String password,String rpassword){
        if(!Objects.equals(password, rpassword)){
            return"password not matching";
        }
        password=passwordEncoder.encode(rpassword);

        User user=userRepository.findByEmail(email).orElseThrow(()->new UserNotFound("user not found"));
        userRepository.updatePassword(user, password);
        ForgetPassword forgetPassword=forgetPasswordRepository.findByUser(user);
        forgetPasswordRepository.delete(forgetPassword);
        return "updated successful";
    }
}
