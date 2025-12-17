package com.example.jewellery.demo.model;



import lombok.Data;

@Data
public class CartItem {

    private String productId;    // Reference to Product ID
    private String productName;  // Product name
    private double price;        // Product price
    private int quantity;        // Quantity user wants to buy
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
    
    
}

