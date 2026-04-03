package com.example.jewellery.demo.service;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.jewellery.demo.model.Banner;
import com.example.jewellery.demo.repository.BannerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Service
public class BannerService {

    @Autowired
    private BannerRepository bannerRepository;

    @Autowired
    private Cloudinary cloudinary;

    // Upload + Save
    public Banner uploadBanner(String title,
                               String subtitle,
                               String description,
                               String price,
                               String oldPrice,
                               MultipartFile file) {

        try {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(),
                    ObjectUtils.emptyMap());

            String imageUrl = uploadResult.get("secure_url").toString();

            Banner banner = new Banner();
            banner.setTitle(title);
            banner.setSubtitle(subtitle);
            banner.setDescription(description);
            banner.setPrice(price);
            banner.setOldPrice(oldPrice);
            banner.setImageUrl(imageUrl);

            return bannerRepository.save(banner);

        } catch (Exception e) {
            throw new RuntimeException("Image upload failed");
        }
    }

    // Get all banners
    public List<Banner> getAllBanners() {
        return bannerRepository.findAll();
    }
    public Banner updateBanner(String id,
            String title,
            String subtitle,
            String price,
            String oldPrice,
            MultipartFile file) {

Banner existing = bannerRepository.findById(id)
.orElseThrow(() -> new RuntimeException("Banner not found"));

try {
// If new image uploaded → upload to Cloudinary
if (file != null && !file.isEmpty()) {
Map uploadResult = cloudinary.uploader().upload(
     file.getBytes(),
     ObjectUtils.emptyMap()
);

String imageUrl = uploadResult.get("secure_url").toString();
existing.setImageUrl(imageUrl);
}



// Update fields
existing.setTitle(title);
existing.setSubtitle(subtitle);
existing.setPrice(price);
existing.setOldPrice(oldPrice);

return bannerRepository.save(existing);

} catch (Exception e) {
throw new RuntimeException("Update failed");
}
}

    // Delete banner
    public void deleteBanner(String id) {
        bannerRepository.deleteById(id);
    }
    
    public Banner getBannerById(String id) {
        return bannerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Banner not found"));
    }
}
