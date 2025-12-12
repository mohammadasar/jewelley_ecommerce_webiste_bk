package com.example.jewellery.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import com.example.jewellery.demo.service.ProductService;
import com.example.jewellery.demo.service.CategoryServiceImpl;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final ProductService productService;
    private final CategoryServiceImpl categoryService;

    // Normal constructor
    public AdminController(ProductService productService, CategoryServiceImpl categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/stats")
    public ResponseEntity<?> stats() {
        long products = productService.getAll().size();
        long categories = categoryService.all().size();

        Map<String, Object> m = new HashMap<>();
        m.put("products", products);
        m.put("categories", categories);

        return ResponseEntity.ok(m);
    }
}
