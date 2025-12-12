package com.example.jewellery.demo.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


public class JwtAuthFilter extends OncePerRequestFilter {
private final JwtUtil jwtUtil;
private final AppUserDetailsService userDetailsService;


public JwtAuthFilter(JwtUtil jwtUtil, AppUserDetailsService userDetailsService) {
this.jwtUtil = jwtUtil;
this.userDetailsService = userDetailsService;
}


@Override
protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
String authHeader = request.getHeader("Authorization");
if (authHeader != null && authHeader.startsWith("Bearer ")) {
String token = authHeader.substring(7);
if (jwtUtil.validateToken(token)) {
String username = jwtUtil.getUsernameFromToken(token);
UserDetails userDetails = userDetailsService.loadUserByUsername(username);
Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
SecurityContextHolder.getContext().setAuthentication(auth);
}
}
filterChain.doFilter(request, response);
}
}