package com.example.jewellery.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.jewellery.demo.model.Invoice;
import com.example.jewellery.demo.model.Order;
import com.example.jewellery.demo.repository.InvoiceRepository;
import com.example.jewellery.demo.repository.OrderRepository;



@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepo;

    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private InvoiceNumberService invoiceNumberService;

    public Invoice generateInvoice(String orderId) {

        Order order = orderRepo.findById(orderId).orElseThrow();

        // Prevent duplicate invoice
        if (invoiceRepo.findByOrderId(orderId) != null) {
            throw new RuntimeException("Invoice already generated");
        }

        Invoice invoice = new Invoice();
        invoice.setInvoiceNumber(invoiceNumberService.generateInvoiceNumber());
        invoice.setOrderId(order.getId());
        invoice.setOrderCode(order.getOrderId());
        invoice.setCustomerWhatsapp(order.getWhatsappNumber());
        invoice.setCustomerAddress(order.getAddress());
        invoice.setItems(order.getItems());

        invoice.setSubTotal(order.getTotalAmount());
        invoice.setTaxAmount(0); // No tax now (can add GST later)
        invoice.setTotalAmount(order.getTotalAmount());

        invoice.setPaymentMode(order.getPaymentMode());
        invoice.setPaymentRefId(order.getPaymentRefId());

        return invoiceRepo.save(invoice);
    }
    
    public Invoice getInvoiceByOrder(String orderId) {
        return invoiceRepo.findByOrderId(orderId);
    }

}
