package com.mohit.authservice.service;

import com.mohit.authservice.dto.EmailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    @Autowired private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private  String sender;
    public void  sendMail(EmailDto emailDto){
        SimpleMailMessage message=new SimpleMailMessage();
        message.setTo(emailDto.to());
        message.setSubject(emailDto.sub());
        message.setText(emailDto.message());
        message.setFrom(sender);
        javaMailSender.send(message);
    }
}
