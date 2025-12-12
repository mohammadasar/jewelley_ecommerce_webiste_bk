package com.example.jewellery.demo.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "carts")
public class Cart {

    @Id
    private String id;
    private String userId;
    private List<CartItem> items = new ArrayList<>();

    // Default constructor
    public Cart() {}

    // Constructor used in service: new Cart(userId)
    public Cart(String userId) {
        this.userId = userId;
        this.items = new ArrayList<>();
    }

    // GETTERS & SETTERS
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public List<CartItem> getItems() { return items; }
    public void setItems(List<CartItem> items) { this.items = items; }

    // ==============================
    // INNER CLASS: CartItem
    // ==============================
    public static class CartItem {

        private String productId;
        private int quantity;
        private double priceAtAdding;

        // Default constructor
        public CartItem() {}

        // GETTERS & SETTERS
        public String getProductId() { return productId; }
        public void setProductId(String productId) { this.productId = productId; }

        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }

        public double getPriceAtAdding() { return priceAtAdding; }
        public void setPriceAtAdding(double priceAtAdding) { this.priceAtAdding = priceAtAdding; }
    }
}
