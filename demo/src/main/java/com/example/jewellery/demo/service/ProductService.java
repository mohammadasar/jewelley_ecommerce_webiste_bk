package com.example.jewellery.demo.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.example.jewellery.demo.model.Product;
import com.example.jewellery.demo.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    // 🔥 Normal constructor (no Lombok)
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product addProduct(Product product) {
        product.setDiscountPercent(calculateDiscount(product.getMrp(), product.getPrice()));
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());
        return productRepository.save(product);
    }

    public Product updateProduct(String id, Product product) {
        return productRepository.findById(id).map(existing -> {
            existing.setProductName(product.getProductName());
            existing.setDescription(product.getDescription());
            existing.setCategoryId(product.getCategoryId());
            existing.setSubCategory(product.getSubCategory());
            existing.setPrice(product.getPrice());
            existing.setMrp(product.getMrp());
            existing.setDiscountPercent(calculateDiscount(product.getMrp(), product.getPrice()));
            existing.setImages(product.getImages());
            existing.setMaterial(product.getMaterial());
            existing.setColor(product.getColor());
            existing.setPlating(product.getPlating());
            existing.setSize(product.getSize());
            existing.setOccasion(product.getOccasion());
            existing.setInStock(product.isInStock());
            existing.setQuantity(product.getQuantity());
            existing.setSku(product.getSku());
            existing.setBrand(product.getBrand());
            existing.setUpdatedAt(LocalDateTime.now());
            return productRepository.save(existing);
        }).orElse(null);
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Optional<Product> getById(String id) {
        return productRepository.findById(id);
    }

    public void deleteById(String id) {
        productRepository.deleteById(id);
    }

    public List<Product> findByCategory(String catId) {
        return productRepository.findByCategoryId(catId);
    }

    public List<Product> searchByName(String productName) {
        return productRepository.findByProductNameContainingIgnoreCase(productName);
    }

    private int calculateDiscount(double mrp, double price) {
        if (mrp <= 0 || price <= 0) return 0;
        return (int) Math.round(((mrp - price) / mrp) * 100);
    }
}
