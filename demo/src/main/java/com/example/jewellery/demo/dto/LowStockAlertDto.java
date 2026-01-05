package com.example.jewellery.demo.dto;



public class LowStockAlertDto {

    private String productId;
    private String productName;
    private int quantity;

    public LowStockAlertDto(String productId, String productName, int quantity) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
    }

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }
}
