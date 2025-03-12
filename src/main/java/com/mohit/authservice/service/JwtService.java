package com.mohit.authservice.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import org.springframework.beans.factory.annotation.Value;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {


    @Value("${app.jwt-secret}")
    private String SECRET_KEY;

    @Value("${app.jwt-expiration}")
    private Long EXPIRATION;
  public  String generateToken(String userName){
      return generateToken(new HashMap<>(),userName);
  }
    private String generateToken(Map<String,Object>extraClaims,String username){
      extraClaims=new HashMap<>(extraClaims);
      return Jwts.builder()
              .setClaims(extraClaims)
              .setSubject(username)
              .setIssuedAt(new Date(System.currentTimeMillis()))
              .setExpiration(new Date(System.currentTimeMillis()+EXPIRATION))
              .signWith(key(), SignatureAlgorithm.HS256)
              .compact();


    }

    private Key key() {
      byte[] keys= Decoders.BASE64.decode(SECRET_KEY);
      return Keys.hmacShaKeyFor(keys);
    }
    public String extractUsername(String token){
      return extractClaim(token,Claims::getSubject);
    }
    public <T> T extractClaim(String token, Function<Claims,T> claimsResolver){
      final Claims claims=extractAllclaims(token);
      return claimsResolver.apply(claims);
    }
    public Claims extractAllclaims(String token){
      return Jwts
              .parserBuilder()
              .setSigningKey(key())
              .build()
              .parseClaimsJws(token)
              .getBody();
    }
    public Date extractExpiration(String token){
        return extractClaim(token,Claims::getExpiration);
    }
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    public boolean isTokenValid(String token, UserDetails userDetails){
      final String username=extractUsername(token);
      return username==userDetails.getUsername()&& !isTokenExpired(token);
    }

}
