package com.example.jewellery.demo.controller;

import com.example.jewellery.demo.model.Category;
import com.example.jewellery.demo.service.CategoryServiceImpl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryControllerApi {

    private final CategoryServiceImpl categoryService;

    public CategoryControllerApi(CategoryServiceImpl categoryService) {
        this.categoryService = categoryService;
    }
    @Value("${app.upload.category}")
    private String uploadDir;



    // -------------------------------------------------------------------
    // ✔ 1. CREATE CATEGORY
    // -------------------------------------------------------------------
    @PostMapping(value = "/add", consumes = "multipart/form-data")
    public ResponseEntity<?> addCategory(
            @RequestPart("category") Category category,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) {
        Category saved = categoryService.add(category, image);
        return ResponseEntity.ok(saved);
    }


    // -------------------------------------------------------------------
    // ✔ 2. GET ALL CATEGORIES
    // -------------------------------------------------------------------
    @GetMapping("/all")
    public ResponseEntity<List<Category>> all() {
        return ResponseEntity.ok(categoryService.all());
    }
    
    
    // -------------------------------------------------------------------
    // ✔ 2. GET ALL CATEGORIES IMAGES
    // -------------------------------------------------------------------
    @GetMapping(
    	    value = "/image-file/{filename}",
    	    produces = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE }
    	)
    	public ResponseEntity<byte[]> getImageFile(@PathVariable String filename) throws IOException {

    	    Path imagePath = Paths.get(uploadDir).resolve(filename);

    	    if (!Files.exists(imagePath)) {
    	        return ResponseEntity.notFound().build();
    	    }

    	    String contentType = Files.probeContentType(imagePath);

    	    if (!MediaType.IMAGE_JPEG_VALUE.equals(contentType)
    	        && !MediaType.IMAGE_PNG_VALUE.equals(contentType)) {
    	        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).build();
    	    }

    	    byte[] imageBytes = Files.readAllBytes(imagePath);

    	    return ResponseEntity.ok()
    	            .contentType(MediaType.parseMediaType(contentType))
    	            .body(imageBytes);
    	}



    // -------------------------------------------------------------------
    // ✔ 3. GET CATEGORY BY ID
    // -------------------------------------------------------------------
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable String id) {
        return categoryService.get(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // -------------------------------------------------------------------
    // ⭐ NEW: 4. GET MAIN CATEGORIES (level = 1)
    // -------------------------------------------------------------------
    @GetMapping("/main")
    public ResponseEntity<List<Category>> getMainCategories() {
        return ResponseEntity.ok(categoryService.getByLevel(1));
    }

    // -------------------------------------------------------------------
    // ⭐ NEW: 5. GET SUBCATEGORIES using parentId
    // -------------------------------------------------------------------
    @GetMapping
    public ResponseEntity<List<Category>> getByFilters(
            @RequestParam(required = false) String parentId,
            @RequestParam(required = false) Integer level,
            @RequestParam(required = false) String project
    ) {
        if (parentId != null) {
            return ResponseEntity.ok(categoryService.getByParentId(parentId));
        }

        

        if (level != null) {
            return ResponseEntity.ok(categoryService.getByLevel(level));
        }

        return ResponseEntity.ok(categoryService.all());
    }

    // -------------------------------------------------------------------
    // ✔ 6. UPDATE CATEGORY
    // -------------------------------------------------------------------
    @PutMapping(value = "/update/{id}", consumes = "multipart/form-data")
    public ResponseEntity<?> updateCategory(
            @PathVariable String id,
            @RequestPart("category") Category updated,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) {
        Category saved = categoryService.update(id, updated, image);

        if (saved != null) {
            return ResponseEntity.ok(saved);
        } else {
            return ResponseEntity.badRequest().body("Category not found");
        }
    }



    // -------------------------------------------------------------------
    // ✔ 7. DELETE CATEGORY
    // -------------------------------------------------------------------
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        categoryService.delete(id);
        return ResponseEntity.ok("deleted");
    }
}
