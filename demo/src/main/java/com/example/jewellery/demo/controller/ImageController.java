package com.example.jewellery.demo.controller;

import com.example.jewellery.demo.service.ImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    // CREATE - Upload
    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) {
        try {
            String url = imageService.upload(file);
            return ResponseEntity.ok(url);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Upload failed");
        }
    }

    // READ - Get image URL by name
    @GetMapping("/{imageName}")
    public ResponseEntity<?> getImage(@PathVariable String imageName) {
        String url = imageService.getImageUrl(imageName);
        if (url == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(url);
    }

    // READ ALL - List all images
    @GetMapping("/all")
    public ResponseEntity<List<String>> getAllImages() {
        return ResponseEntity.ok(imageService.getAllImages());
    }

    // UPDATE - Replace existing image
    @PutMapping("/update/{imageName}")
    public ResponseEntity<?> update(
            @PathVariable String imageName,
            @RequestParam("file") MultipartFile file
    ) {
        try {
            String updatedUrl = imageService.updateImage(imageName, file);
            if (updatedUrl == null) {
                return ResponseEntity.badRequest().body("Image not found");
            }
            return ResponseEntity.ok(updatedUrl);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Update failed");
        }
    }

    // DELETE - Delete image
    @DeleteMapping("/delete/{imageName}")
    public ResponseEntity<?> delete(@PathVariable String imageName) {
        boolean deleted = imageService.deleteImage(imageName);
        if (!deleted) return ResponseEntity.badRequest().body("Image not found");
        return ResponseEntity.ok("Image deleted");
    }
}
