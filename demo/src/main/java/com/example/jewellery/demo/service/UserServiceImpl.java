package com.example.jewellery.demo.service;



import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.example.jewellery.demo.model.User;
import com.example.jewellery.demo.repository.UserRepository;

@Service
public class UserServiceImpl {

    private final UserRepository userRepository;

    // Normal constructor
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(User user) {
        user.setCreatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
