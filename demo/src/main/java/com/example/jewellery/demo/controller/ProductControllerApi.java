package com.example.jewellery.demo.controller;

import com.example.jewellery.demo.model.Product;
import com.example.jewellery.demo.service.ProductService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = {"http://127.0.0.1:5501", "http://localhost:5501"})
public class ProductControllerApi {

    private final ProductService productService;

    public ProductControllerApi(ProductService productService) {
        this.productService = productService;
    }

    // ✅ CREATE
    @PostMapping("/add")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        return ResponseEntity.ok(productService.addProduct(product));
    }

    // ✅ READ ALL
    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAll());
    }

    // ✅ READ BY ID
    // READ - get product by ID
  @GetMapping("/{id}")
  public ResponseEntity<?> getById(@PathVariable String id) {
      Optional<Product> opt = productService.getById(id);
      if (opt.isPresent()) {
          return ResponseEntity.ok(opt.get());
      } else {
          return ResponseEntity.badRequest().body("Product not found");
      }
  }

    // ✅ UPDATE
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody Product product) {
        Product updated = productService.updateProduct(id, product);
        if (updated == null) {
            return ResponseEntity.badRequest().body("Product not found");
        }
        return ResponseEntity.ok(updated);
    }

    // ✅ DELETE
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        productService.deleteById(id);
        return ResponseEntity.ok("Product deleted successfully");
    }

    // ✅ SEARCH
    @GetMapping("/search")
    public ResponseEntity<List<Product>> search(@RequestParam(defaultValue = "") String q) {
        return ResponseEntity.ok(productService.searchByName(q));
    }

    // ✅ FILTER BY CATEGORY
    @GetMapping("/category/{catId}")
    public ResponseEntity<List<Product>> getByCategory(@PathVariable String catId) {
        return ResponseEntity.ok(productService.findByCategory(catId));
    }

    // 🔥 NEW: FILTER BY ATTRIBUTE (IMPORTANT)
    @GetMapping("/filter")
    public ResponseEntity<List<Product>> filterByAttribute(
            @RequestParam String name,
            @RequestParam String value) {

        return ResponseEntity.ok(
                productService.findByAttribute(name, value)
        );
    }
}
//
//    // READ - get product by ID
//    @GetMapping("/{id}")
//    public ResponseEntity<?> getById(@PathVariable String id) {
//        Optional<Product> opt = productService.getById(id);
//        if (opt.isPresent()) {
//            return ResponseEntity.ok(opt.get());
//        } else {
//            return ResponseEntity.badRequest().body("Product not found");
//        }
//    }

   