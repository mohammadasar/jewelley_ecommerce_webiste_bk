package com.example.jewellery.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.jewellery.demo.model.Wishlist;
import com.example.jewellery.demo.repository.WishlistRepository;

@Service
public class WishlistService {

    private final WishlistRepository wishlistRepository;

    // Normal constructor
    public WishlistService(WishlistRepository wishlistRepository) {
        this.wishlistRepository = wishlistRepository;
    }

    public Wishlist getWishlist(String userId) {
    	return wishlistRepository.findByUserId(userId)
    	        .orElseGet(() -> wishlistRepository.save(new Wishlist(userId)));

    }


    public Wishlist add(String userId, String productId) {
        Wishlist w = getWishlist(userId);
        if (!w.getProductIds().contains(productId)) {
            w.getProductIds().add(productId);
        }
        return wishlistRepository.save(w);
    }

    public Wishlist remove(String userId, String productId) {
        Wishlist w = getWishlist(userId);
        w.getProductIds().remove(productId);
        return wishlistRepository.save(w);
    }

    public List<String> getAllProductIds(String userId) {
        return getWishlist(userId).getProductIds();
    }
}
