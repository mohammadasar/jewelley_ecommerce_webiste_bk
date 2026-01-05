package com.example.jewellery.demo.dto;


public class InventoryResponseDto {

    private String productId;
    private String productName;
    private boolean inStock;
    private int quantity;

    public InventoryResponseDto(String productId, String productName, boolean inStock, int quantity) {
        this.productId = productId;
        this.productName = productName;
        this.inStock = inStock;
        this.quantity = quantity;
    }

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public boolean isInStock() {
        return inStock;
    }

    public int getQuantity() {
        return quantity;
    }
}
