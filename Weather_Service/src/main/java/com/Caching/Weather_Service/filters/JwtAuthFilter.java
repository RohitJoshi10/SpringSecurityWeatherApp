package com.Caching.Weather_Service.filters;

import com.Caching.Weather_Service.service.CustomUserDetailsService;
import com.Caching.Weather_Service.util.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Autowired
    JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 1. Extracting the JWT Token from the request. In request, it is present in the Authorization Header as a Bearer Token.
        String authHeader = request.getHeader("Authorization"); // It will be like this: Bearer token
        // Extract the token
        String token = null;
        String username = null;
        if(authHeader != null && authHeader.startsWith("Bearer "))
        {
            token = authHeader.substring(7);
            username = jwtUtil.extractUsername(token);
        }

        // 2. Validate Token: For Validating it we need userDetails We need to extract user from this token. Checking the username extracted matches the one present in the DB.
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null)
        {
            // Fetching the user by username using custom user details service
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

            if(jwtUtil.validateToken(username, userDetails, token))
            {
                // Set to Spring Context: If Token Validated then add it to SecurityContext
                UsernamePasswordAuthenticationToken authToken =  new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()); // (principal, credentials, Authorities)
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Now Just call the next filter
        filterChain.doFilter(request, response);

    }
}
