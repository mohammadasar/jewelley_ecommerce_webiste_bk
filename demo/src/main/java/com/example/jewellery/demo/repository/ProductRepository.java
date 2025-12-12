package com.example.jewellery.demo.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.jewellery.demo.model.Product;

public interface ProductRepository extends MongoRepository<Product, String> {
    List<Product> findByProductNameContainingIgnoreCase(String productName);
    List<Product> findByCategoryId(String categoryId);
}
