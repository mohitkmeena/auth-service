package com.mohit.authservice.dto;
import lombok.Builder;

@Builder
public record EmailDto(String to,String sub,String message) {

}
