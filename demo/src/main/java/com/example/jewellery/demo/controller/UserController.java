package com.example.jewellery.demo.controller;





import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.jewellery.demo.dto.UserProfileDto;
import com.example.jewellery.demo.model.User;
import com.example.jewellery.demo.service.UserServiceImpl;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    // ✅ GET LOGGED-IN USER PROFILE
    @GetMapping("/me")
    public ResponseEntity<UserProfileDto> getMyProfile() {

        User user = userService.getLoggedInUser();

        UserProfileDto dto = new UserProfileDto();
        dto.setName(user.getName());
        dto.setWhatsappNumber(user.getWhatsappNumber());
        dto.setAlternateNumber(user.getAlternateNumber());
        dto.setAddress(user.getAddress());
        dto.setPincode(user.getPincode());
        dto.setState(user.getState());
        dto.setDistrict(user.getDistrict());

        return ResponseEntity.ok(dto);
    }
    
    // ✅ UPDATE LOGGED-IN USER PROFILE
    @PutMapping("/update")
    public ResponseEntity<UserProfileDto> updateProfile(
            @RequestBody UserProfileDto dto) {

        User updated = userService.updateProfile(dto);

        UserProfileDto response = new UserProfileDto();
        response.setName(updated.getName());
        dto.setRoles(updated.getRoles());
        response.setWhatsappNumber(updated.getWhatsappNumber());
        response.setAlternateNumber(updated.getAlternateNumber());
        response.setAddress(updated.getAddress());
        response.setPincode(updated.getPincode());
        response.setState(updated.getState());
        response.setDistrict(updated.getDistrict());

        return ResponseEntity.ok(response);
    }
  
    
    


    

}

