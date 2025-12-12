package com.example.jewellery.demo.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.jewellery.demo.model.Cart;
import com.example.jewellery.demo.model.Product;
import com.example.jewellery.demo.repository.CartRepository;
import com.example.jewellery.demo.repository.ProductRepository;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    // NORMAL CONSTRUCTOR (No Lombok)
    public CartService(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    // Get Cart by user OR create new cart
    public Cart getCartByUser(String userId) {
        return cartRepository.findByUserId(userId)
                .orElseGet(() -> cartRepository.save(new Cart(userId)));
    }

    // ADD TO CART
    public Cart addToCart(String userId, String productId, int qty) {
        Cart cart = getCartByUser(userId);
        Optional<Product> p = productRepository.findById(productId);

        if (p.isEmpty()) {
            throw new RuntimeException("Product not found");
        }

        boolean found = false;

        for (Cart.CartItem item : cart.getItems()) {
            if (item.getProductId().equals(productId)) {
                item.setQuantity(item.getQuantity() + qty);
                found = true;
                break;
            }
        }

        if (!found) {
            Cart.CartItem item = new Cart.CartItem();
            item.setProductId(productId);
            item.setQuantity(qty);
            item.setPriceAtAdding(p.get().getPrice());
            cart.getItems().add(item);
        }

        return cartRepository.save(cart);
    }

    // UPDATE QUANTITY
    public Cart updateQuantity(String userId, String productId, int qty) {
        Cart cart = getCartByUser(userId);

        // Remove existing
        cart.getItems().removeIf(it -> it.getProductId().equals(productId));

        // Add again if quantity > 0
        if (qty > 0) {
            Cart.CartItem newItem = new Cart.CartItem();
            newItem.setProductId(productId);
            newItem.setQuantity(qty);

            productRepository.findById(productId)
                    .ifPresent(p -> newItem.setPriceAtAdding(p.getPrice()));

            cart.getItems().add(newItem);
        }

        return cartRepository.save(cart);
    }

    // REMOVE FROM CART
    public void removeFromCart(String userId, String productId) {
        Cart cart = getCartByUser(userId);
        cart.getItems().removeIf(it -> it.getProductId().equals(productId));
        cartRepository.save(cart);
    }
}
