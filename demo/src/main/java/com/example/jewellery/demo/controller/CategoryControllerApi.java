package com.example.jewellery.demo.controller;

import com.example.jewellery.demo.model.Category;
import com.example.jewellery.demo.service.CategoryServiceImpl;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryControllerApi {

    private final CategoryServiceImpl categoryService;

    public CategoryControllerApi(CategoryServiceImpl categoryService) {
        this.categoryService = categoryService;
    }

    // CREATE
    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody Category c) {
        return ResponseEntity.ok(categoryService.add(c));
    }

    // READ ALL
    @GetMapping("/all")
    public ResponseEntity<List<Category>> all() {
        return ResponseEntity.ok(categoryService.all());
    }

    // READ BY ID
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable String id) {
        return categoryService.get(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // UPDATE (missing before)
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody Category updated) {
        boolean updatedResult = categoryService.update(id, updated);

        if (updatedResult) {
            return ResponseEntity.ok("Category updated");
        } else {
            return ResponseEntity.badRequest().body("Category not found");
        }
    }

    // DELETE
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        categoryService.delete(id);
        return ResponseEntity.ok("deleted");
    }
}
