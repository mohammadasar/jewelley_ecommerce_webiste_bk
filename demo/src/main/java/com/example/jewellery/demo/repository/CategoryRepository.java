package com.example.jewellery.demo.repository;



import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.jewellery.demo.model.Category;

public interface CategoryRepository extends MongoRepository<Category, String> {

}
