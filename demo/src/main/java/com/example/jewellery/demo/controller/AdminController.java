package com.example.jewellery.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import com.example.jewellery.demo.service.ProductService;
import com.example.jewellery.demo.service.UserServiceImpl;
import com.example.jewellery.demo.dto.UserProfileDto;
import com.example.jewellery.demo.model.User;
import com.example.jewellery.demo.service.CategoryServiceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final ProductService productService;
    private final CategoryServiceImpl categoryService;
    private final UserServiceImpl userService;

    // Normal constructor
    public AdminController(ProductService productService, CategoryServiceImpl categoryService,UserServiceImpl userService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/stats")
    public ResponseEntity<?> stats() {
        long products = productService.getAll().size();
        long categories = categoryService.all().size();

        Map<String, Object> m = new HashMap<>();
        m.put("products", products);
        m.put("categories", categories);

        return ResponseEntity.ok(m);
    }
    
    // ✅ GET ALL USERS (ADMIN)
    @GetMapping("/users")
    public ResponseEntity<List<UserProfileDto>> getAllUsers() {
        return ResponseEntity.ok(
            userService.getAllUsers().stream().map(user -> {
                UserProfileDto dto = new UserProfileDto();
                dto.setId(user.getId());  
                dto.setUsername(user.getUsername());
                dto.setEmail(user.getEmail());
                dto.setRoles(user.getRoles());
                dto.setName(user.getName());
                dto.setWhatsappNumber(user.getWhatsappNumber());
                dto.setAlternateNumber(user.getAlternateNumber());
                dto.setAddress(user.getAddress());
                dto.setPincode(user.getPincode());
                dto.setState(user.getState());
                dto.setDistrict(user.getDistrict());
                return dto;
            }).toList()
        );
    }

    // ✅ GET USER BY ID (ADMIN)
    @GetMapping("user/{userId}")
    public ResponseEntity<UserProfileDto> getUserById(@PathVariable String userId) {
        User user = userService.getUserById(userId);
        UserProfileDto dto = new UserProfileDto();
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setRoles(user.getRoles());
        dto.setName(user.getName());
        return ResponseEntity.ok(dto);
    }

    // ✅ UPDATE USER BY ID (ADMIN)
    @PutMapping("/users/{userId}")
    public ResponseEntity<UserProfileDto> updateUserById(
            @PathVariable String userId,
            @RequestBody UserProfileDto dto) {

        User updated = userService.updateUserById(userId, dto);

        UserProfileDto response = new UserProfileDto();
        response.setUsername(updated.getUsername());
        response.setEmail(updated.getEmail());
        response.setRoles(updated.getRoles());
        response.setName(updated.getName());

        return ResponseEntity.ok(response);
    }

    // ✅ DELETE USER (ADMIN)
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable String userId) {
        userService.deleteUserById(userId);
        return ResponseEntity.ok("User deleted successfully");
    }
    
    // ✅ SEARCH USERS (ADMIN)
    @GetMapping("/search")
    public ResponseEntity<List<UserProfileDto>> searchUsers(
            @RequestParam String q) {

        List<UserProfileDto> result = userService.searchUsers(q)
                .stream()
                .map(user -> {
                    UserProfileDto dto = new UserProfileDto();
                    dto.setId(user.getId());
                    dto.setUsername(user.getUsername());
                    dto.setEmail(user.getEmail());
                    dto.setDistrict(user.getDistrict());
                    dto.setState(user.getState());
                    dto.setAddress(user.getAddress());
                    dto.setRoles(user.getRoles());
                    return dto;
                })
                .toList();

        return ResponseEntity.ok(result);
    }

}

