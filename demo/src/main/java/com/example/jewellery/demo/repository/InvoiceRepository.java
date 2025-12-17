package com.example.jewellery.demo.repository;



import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.jewellery.demo.model.Invoice;


public interface InvoiceRepository extends MongoRepository<Invoice, String> {
    long count();
    Invoice findByOrderId(String orderId);
}
