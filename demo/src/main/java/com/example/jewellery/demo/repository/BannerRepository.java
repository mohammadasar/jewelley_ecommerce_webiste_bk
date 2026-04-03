package com.example.jewellery.demo.repository;



import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.jewellery.demo.model.Banner;

public interface BannerRepository extends MongoRepository<Banner, String> {
}