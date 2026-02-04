package com.Caching.Weather_Service.config;

import com.Caching.Weather_Service.entity.Permissions;
import com.Caching.Weather_Service.entity.Role;
import com.Caching.Weather_Service.filters.JwtAuthFilter;
import com.Caching.Weather_Service.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/authenticate").permitAll()
                        .requestMatchers("/api/users/register").permitAll()
                        //.requestMatchers("/api/users/admin/create").hasRole(Role.ADMIN.name())
                        //.requestMatchers("/weather/health").hasRole(Role.ADMIN.name())
                        //        .requestMatchers(HttpMethod.GET, "/weather/**").hasAuthority(Permissions.WEATHER_READ.name())
                        //        .requestMatchers(HttpMethod.POST, "/weather/**").hasAuthority(Permissions.WEATHER_WRITE.name())
                        //        .requestMatchers(HttpMethod.DELETE, "/weather/**").hasAuthority(Permissions.WEATHER_DELETE.name())
                        //        .requestMatchers(HttpMethod.PUT, "/weather/**").hasAuthority(Permissions.WEATHER_WRITE.name())
                        .anyRequest().authenticated());
                //.httpBasic(withDefaults());
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return new CustomUserDetailsService();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        // For this new DAOAuthenticationProvider we will set CustomUserDetailsService
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(daoAuthenticationProvider);
    }

}
