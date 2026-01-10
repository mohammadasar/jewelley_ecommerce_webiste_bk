package com.example.jewellery.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.jewellery.demo.service.CartService;
import com.example.jewellery.demo.model.Cart;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin("*")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // ✅ ADD TO CART
    @PostMapping("/add")
    public ResponseEntity<Cart> addToCart(
            @RequestParam String userId,
            @RequestParam String productId,
            @RequestParam int qty) {

        return ResponseEntity.ok(
                cartService.addToCart(userId, productId, qty)
        );
    }

    // ✅ GET CART
    @GetMapping("/view")
    public ResponseEntity<Cart> viewCart(@RequestParam String userId) {
        return ResponseEntity.ok(cartService.getCartByUser(userId));
    }

    // ✅ REMOVE ITEM
    @PostMapping("/remove")
    public ResponseEntity<String> remove(
            @RequestParam String userId,
            @RequestParam String productId) {

        cartService.removeFromCart(userId, productId);
        return ResponseEntity.ok("Removed successfully");
    }
}
