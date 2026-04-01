package com.example.jewellery.demo.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.example.jewellery.demo.controller.CategoryControllerApi;
import com.example.jewellery.demo.dto.ProductFilterRequest;
import com.example.jewellery.demo.model.Product;
import com.example.jewellery.demo.repository.ProductRepository;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.MongoTemplate;


@Service
public class ProductService {

    private final CategoryControllerApi categoryControllerApi;

    private final ProductRepository productRepository;
    
    private final MongoTemplate mongoTemplate;
    
    
    public List<Product> findByAttribute(String name, String value) {
        return productRepository.findByAttributes(name, value);
    }

    public ProductService(ProductRepository productRepository,
            CategoryControllerApi categoryControllerApi,
            MongoTemplate mongoTemplate) {
this.productRepository = productRepository;
this.categoryControllerApi = categoryControllerApi;
this.mongoTemplate = mongoTemplate;
}

    // ✅ ADD PRODUCT
    public Product addProduct(Product product) {
        product.setDiscountPercent(calculateDiscount(product.getMrp(), product.getPrice()));
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());

        return productRepository.save(product);
    }

    // ✅ UPDATE PRODUCT
    public Product updateProduct(String id, Product product) {
        return productRepository.findById(id).map(existing -> {

            existing.setProductName(product.getProductName());
            existing.setDescription(product.getDescription());

            // ✅ Category
            existing.setCategoryIds(product.getCategoryIds());

            // ✅ Price
            existing.setPrice(product.getPrice());
            existing.setMrp(product.getMrp());
            existing.setDiscountPercent(calculateDiscount(product.getMrp(), product.getPrice()));

            // ✅ Images
            existing.setImages(product.getImages());

            // 🔥 NEW: Dynamic Attributes
            existing.setAttributes(product.getAttributes());

            // 🔥 NEW: Variants (size, color, stock)
            existing.setVariants(product.getVariants());

            // ✅ Stock
            existing.setInStock(product.isInStock());
            existing.setQuantity(product.getQuantity());

            // ✅ Other
            existing.setSku(product.getSku());
            existing.setBrand(product.getBrand());

            existing.setUpdatedAt(LocalDateTime.now());

            return productRepository.save(existing);

        }).orElse(null);
    }

    // ✅ GET ALL
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    // ✅ GET BY ID
    public Optional<Product> getById(String id) {
        return productRepository.findById(id);
    }

    // ✅ DELETE
    public void deleteById(String id) {
        productRepository.deleteById(id);
    }

    // ✅ FILTER BY CATEGORY
    public List<Product> findByCategory(String catId) {
        return productRepository.findByCategoryIdsContaining(catId);
    }

    // ✅ SEARCH
    public List<Product> searchByName(String productName) {
        return productRepository.findByProductNameContainingIgnoreCase(productName);
    }

    // ✅ DISCOUNT CALCULATION
    private int calculateDiscount(double mrp, double price) {
        if (mrp <= 0 || price <= 0) return 0;
        return (int) Math.round(((mrp - price) / mrp) * 100);
    }
    
    public List<Product> filterProducts(ProductFilterRequest request) {
        Query query = new Query();
        // 1. Filter by Categories
        if (request.getCategoryIds() != null && !request.getCategoryIds().isEmpty()) {
            query.addCriteria(Criteria.where("categoryId").in(request.getCategoryIds()));
        }
        // 2. Filter by Price Range
        if (request.getMinPrice() != null || request.getMaxPrice() != null) {
            Criteria priceCriteria = Criteria.where("price");
            if (request.getMinPrice() != null) priceCriteria.gte(request.getMinPrice());
            if (request.getMaxPrice() != null) priceCriteria.lte(request.getMaxPrice());
            query.addCriteria(priceCriteria);
        }
        // 3. Filter by Dynamic Attributes (Material, Color, etc.)
        if (request.getAttributes() != null && !request.getAttributes().isEmpty()) {
            for (Map.Entry<String, List<String>> entry : request.getAttributes().entrySet()) {
                
                // Assuming your attributes in DB are stored as an array of objects
                // [{ "key": "Material", "value": "Gold" }]
                query.addCriteria(Criteria.where("attributes").elemMatch(
                    Criteria.where("key").is(entry.getKey())
                            .and("value").in(entry.getValue())
                ));
            }
        }
        // execute query via mongoTemplate
        return mongoTemplate.find(query, Product.class);
    }
}
