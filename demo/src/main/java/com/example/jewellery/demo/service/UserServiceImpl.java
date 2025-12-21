package com.example.jewellery.demo.service;



import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.jewellery.demo.dto.UserProfileDto;
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
    // ✅ GET LOGGED-IN USER
    public User getLoggedInUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            throw new RuntimeException("User not authenticated");
        }

        String username = auth.getName(); // username from JWT
        return userRepository.findByUsername(username);
    }
    // ✅ UPDATE LOGGED-IN USER PROFILE
    public User updateProfile(UserProfileDto dto) {

        User user = getLoggedInUser();

        user.setName(dto.getName());
        user.setWhatsappNumber(dto.getWhatsappNumber());
        user.setAlternateNumber(dto.getAlternateNumber());
        user.setAddress(dto.getAddress());
        user.setPincode(dto.getPincode());
        user.setState(dto.getState());
        user.setDistrict(dto.getDistrict());

        return userRepository.save(user);
    }
    
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
    public void deleteUserById(String userId) {
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(userId);
    }


    // ✅ ADMIN: UPDATE USER BY ID
    public User updateUserById(String userId, UserProfileDto dto) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 🔹 Update only allowed fields
        if (dto.getUsername() != null) {
            user.setUsername(dto.getUsername());
        }

        if (dto.getEmail() != null) {
            user.setEmail(dto.getEmail());
        }

        if (dto.getRoles() != null && !dto.getRoles().isEmpty()) {
            user.setRoles(dto.getRoles());
        }

        if (dto.getName() != null) {
            user.setName(dto.getName());
        }

        if (dto.getWhatsappNumber() != null) {
            user.setWhatsappNumber(dto.getWhatsappNumber());
        }

        if (dto.getAlternateNumber() != null) {
            user.setAlternateNumber(dto.getAlternateNumber());
        }

        if (dto.getAddress() != null) {
            user.setAddress(dto.getAddress());
        }

        if (dto.getPincode() != null) {
            user.setPincode(dto.getPincode());
        }

        if (dto.getState() != null) {
            user.setState(dto.getState());
        }

        if (dto.getDistrict() != null) {
            user.setDistrict(dto.getDistrict());
        }

        return userRepository.save(user);
    }

    public List<User> searchUsers(String keyword) {
        return userRepository.searchUsers(keyword);
    }


}
