package com.mohit.authservice.config;

import com.mohit.authservice.service.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@EnableMethodSecurity
public class SecurityConfig  {
@Autowired
private JwtFilter jwtFilter;

  @Bean
  public PasswordEncoder passwordEncoder(){
      return new BCryptPasswordEncoder(12);
  }
    @Autowired
    private UserDetailsImpl userDetailsService;

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authp=new DaoAuthenticationProvider();
        authp.setUserDetailsService(userDetailsService);
        authp.setPasswordEncoder(passwordEncoder());
        return authp;

    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{

        return  config.getAuthenticationManager();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
       return http.csrf(customizer->customizer.disable())
                .authorizeHttpRequests(
                        auths->
                                auths.requestMatchers("/v1/auth/**").permitAll()
                                        .requestMatchers("/v1/password/**").permitAll()
                                        .requestMatchers("/v1/ping/**").permitAll()
                                        .anyRequest().authenticated())
               .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
               .authenticationProvider(authenticationProvider())
               .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
               .build();

    }
}
