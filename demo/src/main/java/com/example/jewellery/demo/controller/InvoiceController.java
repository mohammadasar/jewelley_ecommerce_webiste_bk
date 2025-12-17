package com.example.jewellery.demo.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.jewellery.demo.model.Invoice;
import com.example.jewellery.demo.service.InvoiceService;



@RestController
@RequestMapping("/api/invoices")
@CrossOrigin(origins = "http://127.0.0.1:5501")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    // Generate invoice after payment confirmation
    @PostMapping("/admin/generate/{orderId}")
    public Invoice generateInvoice(@PathVariable String orderId) {
        return invoiceService.generateInvoice(orderId);
    }

    // Get invoice by order ID
    @GetMapping("/order/{orderId}")
    public Invoice getInvoiceByOrder(@PathVariable String orderId) {
        return invoiceService.getInvoiceByOrder(orderId);
    }
}
