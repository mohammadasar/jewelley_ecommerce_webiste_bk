package com.example.jewellery.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.Customizer;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final AppUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    public SecurityConfig(AppUserDetailsService userDetailsService, JwtUtil jwtUtil) {
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        JwtAuthFilter jwtFilter = new JwtAuthFilter(jwtUtil, userDetailsService);

        http
            .cors(Customizer.withDefaults())    // ✅ VERY IMPORTANT (THIS LINE WAS MISSING)
           
            .csrf(csrf -> csrf.disable()) // Disable CSRF
            .authorizeHttpRequests(auth -> auth
//                .requestMatchers("/api/auth/**", "/api/products/**", "/uploads/**").permitAll()
//                .requestMatchers("/api/admin/**","/api/categories/**","/api/categories/image/**",
//                		"/uploads/**","/api/products/**",
//                		"/api/images/**","/api/orders/**","/api/orders/admin/**"
//                		,"/api/invoices/admin/**","/api/invoices/**").permitAll()
            		
            		 // Public
            	    .requestMatchers(
            	        "/api/auth/**",
            	        "/api/products/**",
            	        "/api/categories/all",
            	        "/api/categories/image-file/**",
            	        "/uploads/**"
            	    ).permitAll()

            	    // Admin only
            	    .requestMatchers(
            	        "/api/admin/**",
            	        "/api/categories/**",
            	        "/api/images/**",
            	        "/api/orders/admin/**",
            	        "/api/inventory/**",
            	        "/api/invoices/admin/**"
            	    ).hasRole("ADMIN")

            	    // User (logged-in customers)
            	    .requestMatchers(
            	        "/api/orders/**",
            	        "/api/invoices/**",
            	        "/api/user/**",
            	        "/api/wishlist/**",
            	        "/api/cart/**"
            	    ).hasAnyRole("USER", "ADMIN")

            	   
                
//            	    .requestMatchers("/api/user/me").permitAll()

                
//                .requestMatchers("/api/wishlist/**").permitAll()

                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            );

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}
