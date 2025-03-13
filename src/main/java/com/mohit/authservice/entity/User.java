package com.mohit.authservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "user")
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String username;
    @Size(min = 6,message = "password length must be 6 or longer ")
    private String password;
    private String name;
    private String email;
}
