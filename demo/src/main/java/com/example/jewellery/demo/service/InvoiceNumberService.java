package com.example.jewellery.demo.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.jewellery.demo.repository.InvoiceRepository;


@Service
public class InvoiceNumberService {

    @Autowired
    private InvoiceRepository invoiceRepo;

    public String generateInvoiceNumber() {
        long count = invoiceRepo.count() + 1;
        return "INV-2025-" + String.format("%04d", count);
    }
}
