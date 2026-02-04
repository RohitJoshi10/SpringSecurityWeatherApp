package com.Caching.Weather_Service.controller;

import com.Caching.Weather_Service.entity.AuthRequest;
import com.Caching.Weather_Service.util.JWTUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager; // We have already created a bean of it in the security config we need it as we want to call it directly from the AuthController.

    @Autowired
    JWTUtil jwtUtil;

    @PostMapping("/authenticate") // or Login
    public String generateToken(@RequestBody AuthRequest authRequest){

            // 1. Authenticate
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );

            // 2. Create JWT Token: Spring does not have support for JWT Utility so we need to create that which will have all functionalities like extract token, decoded token, verify signature etc.
            //    And Inorder to create the Utility we need some libraries like jjwt and some other which will have all those methods we will add library in our pom.
            return jwtUtil.generateToken(authRequest.getUsername());
        } catch (Exception e) {
            throw new BadCredentialsException("Username & Password are not correct.");
        }

    }
}
