package com.example.jewellery.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.jewellery.demo.model.Category;
import com.example.jewellery.demo.repository.CategoryRepository;

@Service
public class CategoryServiceImpl {

    private final CategoryRepository categoryRepository;

    // Normal constructor
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category add(Category category) {
        return categoryRepository.save(category);
    }

    public List<Category> all() {
        return categoryRepository.findAll();
    }

    public Optional<Category> get(String id) {
        return categoryRepository.findById(id);
    }

    public void delete(String id) {
        categoryRepository.deleteById(id);
    }
    public boolean update(String id, Category updatedCategory) {
        return categoryRepository.findById(id).map(existing -> {
            existing.setName(updatedCategory.getName());
            existing.setParentId(updatedCategory.getParentId());
            existing.setDescription(updatedCategory.getDescription());
            categoryRepository.save(existing);
            return true;
        }).orElse(false);
    }

}
