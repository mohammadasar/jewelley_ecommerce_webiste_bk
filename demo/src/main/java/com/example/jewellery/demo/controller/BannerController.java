package com.example.jewellery.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.jewellery.demo.model.Banner;
import com.example.jewellery.demo.service.BannerService;

import java.util.List;

@RestController
@RequestMapping("/api/banners")
@CrossOrigin("*")
public class BannerController {

    @Autowired
    private BannerService bannerService;

    // Upload banner
    @PostMapping("/upload")
    public ResponseEntity<Banner> uploadBanner(
            @RequestParam String title,
            @RequestParam String subtitle,
            @RequestParam String description,
            @RequestParam String price,
            @RequestParam String oldPrice,
            @RequestParam MultipartFile image
    ) {
        Banner banner = bannerService.uploadBanner(
                title, subtitle, description, price, oldPrice, image
        );
        return ResponseEntity.ok(banner);
    }

    // Get all banners
    @GetMapping
    public ResponseEntity<List<Banner>> getAllBanners() {
        return ResponseEntity.ok(bannerService.getAllBanners());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Banner> getBannerById(@PathVariable String id) {
        Banner banner = bannerService.getBannerById(id);
        return ResponseEntity.ok(banner);
    }
    
//    update
    @PutMapping("/update/{id}")
    public ResponseEntity<Banner> updateBanner(
            @PathVariable String id,
            @RequestParam String title,
            @RequestParam String subtitle,
            @RequestParam String price,
            @RequestParam String oldPrice,
            @RequestParam(required = false) MultipartFile image
    ) {
        Banner updatedBanner = bannerService.updateBanner(
                id, title, subtitle, price, oldPrice, image
        );
        return ResponseEntity.ok(updatedBanner);
    }

    // Delete banner
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBanner(@PathVariable String id) {
        bannerService.deleteBanner(id);
        return ResponseEntity.ok("Deleted successfully");
    }
}