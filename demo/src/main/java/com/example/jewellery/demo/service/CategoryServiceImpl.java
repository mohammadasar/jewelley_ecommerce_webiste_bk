package com.example.jewellery.demo.service;


import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.jewellery.demo.model.Category;
import com.example.jewellery.demo.repository.CategoryRepository;

@Service
public class CategoryServiceImpl {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    private final String uploadDir = "uploads/categories";

    // -----------------------------------------------------
    // ✔ ADD CATEGORY
    // Automatically sets level if parent exists
    // -----------------------------------------------------
    // ✅ ADD CATEGORY WITH IMAGE
    public Category add(Category category, MultipartFile image) {

        // Auto-set level
        if (category.getParentId() == null || category.getParentId().isEmpty()) {
            category.setLevel(1);
        } else {
            categoryRepository.findById(category.getParentId())
                    .ifPresent(parent -> category.setLevel(parent.getLevel() + 1));
        }

        // ✅ Handle image
        if (image != null && !image.isEmpty()) {
            // TEMP solution (store image name)
            String imageUrl = "/uploads/categories/" + image.getOriginalFilename();
            category.setImageUrl(imageUrl);

            // ⚠️ Later: save image to disk / cloud
        }

        return categoryRepository.save(category);
    }

    // -----------------------------------------------------
    // ✔ GET ALL CATEGORIES
    // -----------------------------------------------------
    public List<Category> all() {
        return categoryRepository.findAll();
    }

    // -----------------------------------------------------
    // ✔ GET CATEGORY BY ID
    // -----------------------------------------------------
    public Optional<Category> get(String id) {
        return categoryRepository.findById(id);
    }

    // -----------------------------------------------------
    // ✔ DELETE CATEGORY
    // -----------------------------------------------------
    public void delete(String id) {
        categoryRepository.deleteById(id);
    }

    // -----------------------------------------------------
    public Category update(String id, Category updated, MultipartFile image) {

        return categoryRepository.findById(id).map(existing -> {

            existing.setName(updated.getName());
            existing.setDescription(updated.getDescription());
            existing.setParentId(updated.getParentId());

            // level logic
            if (updated.getParentId() == null || updated.getParentId().isEmpty()) {
                existing.setLevel(1);
            } else {
                categoryRepository.findById(updated.getParentId())
                    .ifPresent(parent -> existing.setLevel(parent.getLevel() + 1));
            }

            // image update
            if (image != null && !image.isEmpty()) {
                String imageUrl = saveImage(image); // your image save logic
                existing.setImageUrl(imageUrl);
            }

            return categoryRepository.save(existing);

        }).orElse(null);
    }

    private String saveImage(MultipartFile image) {
        try {
            // Ensure directory exists
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Generate unique filename
            String originalName = image.getOriginalFilename();
            String extension = originalName.substring(originalName.lastIndexOf("."));
            String fileName = UUID.randomUUID() + extension;

            // Save file
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(image.getInputStream(), filePath);

            // Return URL path (NOT file system path)
            return "/api/categories/image-file/" + fileName;

        } catch (Exception e) {
            throw new RuntimeException("Failed to store image", e);
        }
    }

	// -----------------------------------------------------
    // ⭐ NEW: GET BY LEVEL
    // -----------------------------------------------------
    public List<Category> getByLevel(int level) {
        return categoryRepository.findByLevel(level);
    }

    // -----------------------------------------------------
    // ⭐ NEW: GET BY PARENT ID (Subcategories)
    // -----------------------------------------------------
    public List<Category> getByParentId(String parentId) {
        return categoryRepository.findByParentId(parentId);
    }



   

}
