package com.example.jewellery.demo.dto;


import java.util.List;
import java.util.Map;

public class ProductFilterRequest {
    private List<String> categoryIds;
    private Double minPrice;
    private Double maxPrice;
    
    // Map of Attribute Name -> List of selected values
    // e.g. "Material" -> ["Gold", "Silver"], "Size" -> ["Free Size"]
    private Map<String, List<String>> attributes; 
    
    private String sortBy; // e.g., "price_asc", "price_desc", "newest"

    // Getters and Setters
    public List<String> getCategoryIds() { return categoryIds; }
    public void setCategoryIds(List<String> categoryIds) { this.categoryIds = categoryIds; }
    
    public Double getMinPrice() { return minPrice; }
    public void setMinPrice(Double minPrice) { this.minPrice = minPrice; }
    
    public Double getMaxPrice() { return maxPrice; }
    public void setMaxPrice(Double maxPrice) { this.maxPrice = maxPrice; }

    public Map<String, List<String>> getAttributes() { return attributes; }
    public void setAttributes(Map<String, List<String>> attributes) { this.attributes = attributes; }

    public String getSortBy() { return sortBy; }
    public void setSortBy(String sortBy) { this.sortBy = sortBy; }
}

