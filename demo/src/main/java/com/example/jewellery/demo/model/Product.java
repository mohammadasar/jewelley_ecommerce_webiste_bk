package com.example.jewellery.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@Document(collection = "products")
public class Product {
@Id
private String id;
private String productName;
private String description;
private List<String> categoryIds;// reference to Category id
//private String subCategory;
private String categoryPath;   // breadcrumb path

private double price;
private double mrp;
private int discountPercent;


private List<String> images = new ArrayList<>();


private String material; // Alloy, Brass, Copper, Stainless Steel
private String color; // Gold, Rose Gold, Silver, Multicolor
private String plating; // 1g gold plated, antique, matte
private String size; // Free size, S, M, L
private String occasion; // Bridal, Party, Daily


private boolean inStock = true;
private int quantity = 0;


private String sku;
private String brand;


private LocalDateTime createdAt;
private LocalDateTime updatedAt;
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public String getProductName() {
	return productName;
}
public void setProductName(String productName) {
	this.productName = productName;
}
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}

public List<String> getCategoryIds() {
	return categoryIds;
}
public void setCategoryIds(List<String> categoryIds) {
	this.categoryIds = categoryIds;
}


public String getCategoryPath() {
	return categoryPath;
}
public void setCategoryPath(String categoryPath) {
	this.categoryPath = categoryPath;
}
public double getPrice() {
	return price;
}
public void setPrice(double price) {
	this.price = price;
}
public double getMrp() {
	return mrp;
}
public void setMrp(double mrp) {
	this.mrp = mrp;
}
public int getDiscountPercent() {
	return discountPercent;
}
public void setDiscountPercent(int discountPercent) {
	this.discountPercent = discountPercent;
}
public List<String> getImages() {
	return images;
}
public void setImages(List<String> images) {
	this.images = images;
}
public String getMaterial() {
	return material;
}
public void setMaterial(String material) {
	this.material = material;
}
public String getColor() {
	return color;
}
public void setColor(String color) {
	this.color = color;
}
public String getPlating() {
	return plating;
}
public void setPlating(String plating) {
	this.plating = plating;
}
public String getSize() {
	return size;
}
public void setSize(String size) {
	this.size = size;
}
public String getOccasion() {
	return occasion;
}
public void setOccasion(String occasion) {
	this.occasion = occasion;
}
public boolean isInStock() {
	return inStock;
}
public void setInStock(boolean inStock) {
	this.inStock = inStock;
}
public int getQuantity() {
	return quantity;
}
public void setQuantity(int quantity) {
	this.quantity = quantity;
}
public String getSku() {
	return sku;
}
public void setSku(String sku) {
	this.sku = sku;
}
public String getBrand() {
	return brand;
}
public void setBrand(String brand) {
	this.brand = brand;
}
public LocalDateTime getCreatedAt() {
	return createdAt;
}
public void setCreatedAt(LocalDateTime createdAt) {
	this.createdAt = createdAt;
}
public LocalDateTime getUpdatedAt() {
	return updatedAt;
}
public void setUpdatedAt(LocalDateTime updatedAt) {
	this.updatedAt = updatedAt;
}


}
