package com.example.jewellery.demo.service;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService {

    private final Environment env;

    public ImageService(Environment env) {
        this.env = env;
    }

    // -----------------------------
    // CREATE (Upload)
    // -----------------------------
    public String upload(MultipartFile file) throws IOException {
        String cloudName = env.getProperty("cloudinary.cloud-name");

        if (cloudName != null && !cloudName.isBlank()) {
            return uploadToCloudinary(file);
        } else {
            return saveLocal(file);
        }
    }

    // -----------------------------
    // READ (Get image URL)
    // -----------------------------
    public String getImageUrl(String imageName) {
        String uploadDir = env.getProperty("file.upload-dir", "uploads");
        Path filePath = Paths.get(uploadDir).resolve(imageName);

        if (Files.exists(filePath)) {
            return "/" + uploadDir + "/" + imageName;
        }
        return null;
    }

    // -----------------------------
    // READ ALL (List all images)
    // -----------------------------
    public List<String> getAllImages() {
        String uploadDir = env.getProperty("file.upload-dir", "uploads");
        Path uploadPath = Paths.get(uploadDir);

        List<String> list = new ArrayList<>();

        if (Files.exists(uploadPath)) {
            try {
                Files.list(uploadPath).forEach(path -> list.add(path.getFileName().toString()));
            } catch (IOException ignored) {}
        }

        return list;
    }

    // -----------------------------
    // UPDATE (Replace image)
    // -----------------------------
    public String updateImage(String existingImageName, MultipartFile newFile) throws IOException {
        String uploadDir = env.getProperty("file.upload-dir", "uploads");
        Path uploadPath = Paths.get(uploadDir);
        Path oldFilePath = uploadPath.resolve(existingImageName);

        if (!Files.exists(oldFilePath)) {
            return null; // image not found
        }

        // delete old one
        Files.delete(oldFilePath);

        // save new one
        String newFilename = System.currentTimeMillis() + "_" + newFile.getOriginalFilename();
        Path newFilePath = uploadPath.resolve(newFilename);

        Files.copy(newFile.getInputStream(), newFilePath);

        return "/" + uploadDir + "/" + newFilename;
    }

    // -----------------------------
    // DELETE (Remove image)
    // -----------------------------
    public boolean deleteImage(String imageName) {
        String uploadDir = env.getProperty("file.upload-dir", "uploads");
        Path uploadPath = Paths.get(uploadDir);
        Path filePath = uploadPath.resolve(imageName);

        try {
            return Files.deleteIfExists(filePath);
        } catch (IOException e) {
            return false;
        }
    }

    // -----------------------------
    // LOCAL SAVE (used by upload)
    // -----------------------------
    private String saveLocal(MultipartFile file) throws IOException {
        String uploadDir = env.getProperty("file.upload-dir", "uploads");
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath))
            Files.createDirectories(uploadPath);

        String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = uploadPath.resolve(filename);

        Files.copy(file.getInputStream(), filePath);

        return "/" + uploadDir + "/" + filename;
    }

    // -----------------------------
    // CLOUDINARY UPLOAD
    // -----------------------------
    @SuppressWarnings("rawtypes")
    private String uploadToCloudinary(MultipartFile file) throws IOException {

        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", env.getProperty("cloudinary.cloud-name"));
        config.put("api_key", env.getProperty("cloudinary.api-key"));
        config.put("api_secret", env.getProperty("cloudinary.api-secret"));

        com.cloudinary.Cloudinary cloudinary = new com.cloudinary.Cloudinary(config);

        Map uploadResult = cloudinary.uploader().upload(
                file.getBytes(),
                com.cloudinary.utils.ObjectUtils.emptyMap()
        );

        return (String) uploadResult.get("secure_url");
    }
}
