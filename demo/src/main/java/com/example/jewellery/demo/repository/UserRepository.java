package com.example.jewellery.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.jewellery.demo.model.User;

public interface UserRepository extends MongoRepository<User, String> {
User findByUsername(String username);
boolean existsByUsername(String username);
boolean existsByEmail(String email);
}