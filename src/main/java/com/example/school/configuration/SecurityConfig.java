package com.example.school.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.servlet.DispatcherType;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Value("${spring.security.debug:false}")
    boolean securityDebug;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
      
        http
        .csrf(csrf -> csrf.disable())  
        .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))                     
            .authorizeHttpRequests(authorize -> authorize
            .requestMatchers("/swagger-ui/**", "/v3/api-docs/**","/swagger-ui.html")
            .permitAll()
            .requestMatchers(HttpMethod.POST, "/login").permitAll()
            .requestMatchers(HttpMethod.DELETE).hasRole("ADMIN")
            .requestMatchers(HttpMethod.PUT).hasRole("ADMIN")
            .requestMatchers(HttpMethod.POST).hasRole("ADMIN") 
            .requestMatchers(HttpMethod.GET).hasAnyRole("USER", "ADMIN")               
            .anyRequest().authenticated())
            .httpBasic(Customizer.withDefaults());
            

        return http.build();
    }

}
