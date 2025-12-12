package com.example.jewellery.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.jewellery.demo.service.CartService;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    // Normal constructor
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // Remove item from cart
    @PostMapping("/remove")
    public ResponseEntity<?> remove(@RequestParam String userId, @RequestParam String productId) {
        cartService.removeFromCart(userId, productId);
        return ResponseEntity.ok("removed");
    }
}
