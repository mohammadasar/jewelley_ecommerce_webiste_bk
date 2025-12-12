package com.example.jewellery.demo.controller;

import com.example.jewellery.demo.dto.JwtResponse;
import com.example.jewellery.demo.dto.LoginRequest;
import com.example.jewellery.demo.dto.SignupRequest;
import com.example.jewellery.demo.model.User;

import com.example.jewellery.demo.repository.UserRepository;
import com.example.jewellery.demo.security.JwtUtil;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    private final BCryptPasswordEncoder passwordEncoder;

    // Normal constructor for dependency injection
    public AuthController(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest req) {
        if (userRepository.existsByUsername(req.getUsername())) {
            return ResponseEntity.badRequest().body("Username taken");
        }
        if (userRepository.existsByEmail(req.getEmail())) {
            return ResponseEntity.badRequest().body("Email taken");
        }

        User user = new User();
        user.setUsername(req.getUsername());
        user.setEmail(req.getEmail());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.getRoles().add("ROLE_USER");
        user.setCreatedAt(LocalDateTime.now());
        
        userRepository.save(user);
        return ResponseEntity.ok("User registered");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        User user = userRepository.findByUsername(req.getUsername());
        if (user == null || !passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            return ResponseEntity.badRequest().body("Invalid credentials");
        }

        String token = jwtUtil.generateToken(user);
        JwtResponse res = new JwtResponse();
        res.setToken(token);
        res.setUsername(user.getUsername());
        res.setRoles(user.getRoles());

        return ResponseEntity.ok(res);
    }
}
