package com.example.jewellery.demo.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.jewellery.demo.model.Category;

public interface CategoryRepository extends MongoRepository<Category, String> {

    List<Category> findByLevel(int level);

    List<Category> findByParentId(String parentId);
}
