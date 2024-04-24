package com.sejjari.gesrdv.securite;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
    public class JWTgenerator {


        public String generateToken(Authentication authentication){
            User user = (User) authentication.getPrincipal();
            String username = user.getUsername();
            Date currentDate = new Date();
            System.out.println("Roles: " + authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(", ")));

            Date expireDate = new Date(currentDate.getTime() + SecurityConstants.JWT_EXPIRATION);
            String token = Jwts.builder()
                    .setSubject(username)
                    .setIssuedAt(new Date())
                    .claim("roles",user.getAuthorities().stream().map(grantedAuthority -> grantedAuthority.getAuthority()).collect(Collectors.toList()))
                    .setExpiration(expireDate)
                    .signWith(SignatureAlgorithm.HS256, SecurityConstants.JWT_SECRET)
                    .compact();
            System.out.println(token);


            return token;
        }

        public String getUsernameFromJWT(String token) {
            try{


            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(SecurityConstants.JWT_SECRET)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject();}
            catch (Exception ex){
                throw new AuthenticationCredentialsNotFoundException("JWT was   incorrect", ex.fillInStackTrace());

            }
        }

        public boolean validateToken(String token) {
            try {
                Jwts.parserBuilder()
                        .setSigningKey(SecurityConstants.JWT_SECRET)
                        .build()
                        .parseClaimsJws(token);
                return true;
            } catch (Exception ex) {
                throw new AuthenticationCredentialsNotFoundException("JWT was exprired or incorrect", ex.fillInStackTrace());
            }
        }

    }

