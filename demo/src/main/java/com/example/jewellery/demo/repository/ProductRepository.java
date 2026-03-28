package com.example.jewellery.demo.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.example.jewellery.demo.model.Product;

public interface ProductRepository extends MongoRepository<Product, String> {
    List<Product> findByProductNameContainingIgnoreCase(String productName);
    // 🔥 MULTI-CATEGORY FILTER
    List<Product> findByCategoryIdsContaining(String categoryId);
    List<Product> findByBrand(String brand);

    List<Product> findByInStock(boolean inStock);
    
    List<Product> findByQuantityLessThan(int quantity);
    
    @Query("{ 'attributes': { $elemMatch: { name: ?0, value: ?1 } } }")
    List<Product> findByAttributes(String name, String value);
}
