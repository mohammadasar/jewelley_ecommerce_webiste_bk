package com.example.jewellery.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.jewellery.demo.repository.OrderRepository;



@Service
public class OrderIdService {

    @Autowired
    private OrderRepository orderRepo;

    public String generateOrderId() {
        long count = orderRepo.count() + 1;
        return "ORD-2025-" + String.format("%04d", count);
    }
}
