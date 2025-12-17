package com.example.jewellery.demo.repository;



import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.jewellery.demo.model.Order;


public interface OrderRepository extends MongoRepository<Order, String> {
    long count();
    List<Order> findByStatus(String status);

}
