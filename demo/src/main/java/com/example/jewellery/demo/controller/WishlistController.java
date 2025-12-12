package com.example.jewellery.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.jewellery.demo.service.WishlistService;


@CrossOrigin(origins = "http://127.0.0.1:5501")
@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {

    private final WishlistService wishlistService;

    // Normal constructor
    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @GetMapping
    public ResponseEntity<?> get(@RequestParam String userId) {
        return ResponseEntity.ok(wishlistService.getWishlist(userId));
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestParam String userId, @RequestParam String productId) {
        return ResponseEntity.ok(wishlistService.add(userId, productId));
    }

    @PostMapping("/remove")
    public ResponseEntity<?> remove(@RequestParam String userId, @RequestParam String productId) {
        return ResponseEntity.ok(wishlistService.remove(userId, productId));
    }
}
