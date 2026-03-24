package com.example.jewellery.demo.controller;



import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.jewellery.demo.model.Order;
import com.example.jewellery.demo.model.User;
import com.example.jewellery.demo.repository.OrderRepository;
import com.example.jewellery.demo.service.InvoiceService;
import com.example.jewellery.demo.service.OrderIdService;
import com.example.jewellery.demo.service.UserServiceImpl;


@RestController
@RequestMapping("/api/orders")
@CrossOrigin
public class OrderController {

    private final InvoiceService invoiceService;

    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private OrderIdService orderIdService;

    OrderController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    // ✅ PLACE ORDER
//    @PostMapping("/place")
//    public Order placeOrder(@Valid @RequestBody Order order) {
//
//        order.setOrderId(orderIdService.generateOrderId());
//
//        // calculate total using double
//        double total = order.getItems()
//                .stream()
//                .mapToDouble(i -> i.getPrice() * i.getQuantity())
//                .sum();
//
//        order.setTotalAmount(total);
//
//        return orderRepo.save(order);
//    }
    @Autowired
    private UserServiceImpl userService;  // inject your existing user service

    @PostMapping("/place")
    public Order placeOrder(@Valid @RequestBody Order order) {

        // 1️⃣ Get logged-in user
        User loggedInUser = userService.getLoggedInUser();
        if (loggedInUser == null) {
            throw new RuntimeException("User must be logged in to place order");
        }

        // 2️⃣ Set userId
        order.setUserId(loggedInUser.getId());

        // 3️⃣ Generate order ID
        order.setOrderId(orderIdService.generateOrderId());

        // 4️⃣ Calculate total
        double total = order.getItems()
                .stream()
                .mapToDouble(i -> i.getPrice() * i.getQuantity())
                .sum();
        order.setTotalAmount(total);

        // 5️⃣ Save order
        return orderRepo.save(order);
    }
     
    @GetMapping("/my-orders")
    public List<Order> getMyOrders() {

        // 1️⃣ Get logged-in user
        User loggedInUser = userService.getLoggedInUser();

        if (loggedInUser == null) {
            throw new RuntimeException("User not logged in");
        }

        // 2️⃣ Fetch orders using userId
        return orderRepo.findByUserId(loggedInUser.getId());
    }

    // 🔍 ADMIN – GET ALL ORDERS
    @GetMapping("/admin/all")
    public List<Order> getAllOrders() {
        return orderRepo.findAll();
    }

    // 🔍 GET SINGLE ORDER
    @GetMapping("/{id}")
    public Order getOrder(@PathVariable String id) {
        return orderRepo.findById(id).orElse(null);
    }

    // 🔄 UPDATE ORDER STATUS (ADMIN)
    @PutMapping("/admin/status/{id}")
    public Order updateStatus(
            @PathVariable String id,
            @RequestParam String status) {

        Order order = orderRepo.findById(id).orElseThrow();
        order.setStatus(status);
        return orderRepo.save(order);
    }
    
//    @PutMapping("/admin/confirm-payment/{id}")
//    public Order confirmPayment(
//            @PathVariable String id,
//            @RequestParam String paymentMode,
//            @RequestParam(required = false) String paymentRefId) {
//
//        Order order = orderRepo.findById(id).orElseThrow();
//
//        order.setStatus("CONFIRMED");
//        order.setPaymentMode(paymentMode);
//        order.setPaymentRefId(paymentRefId);
//        order.setPaidAt(LocalDateTime.now());
//
//        return orderRepo.save(order);
//    }
    @PutMapping("/admin/confirm-payment/{id}")
    public Order confirmPayment(@PathVariable String id,
                                @RequestParam String paymentMode,
                                @RequestParam String paymentRefId) {

        Order order = orderRepo.findById(id).orElseThrow();

        order.setStatus("CONFIRMED");
        order.setPaymentMode(paymentMode);
        order.setPaymentRefId(paymentRefId);
        order.setPaidAt(LocalDateTime.now());

        Order saved = orderRepo.save(order);

        // 🔥 AUTO GENERATE INVOICE
        invoiceService.generateInvoice(id);

        return saved;
    }

    
    @GetMapping("/admin/sales-report")
    public double getTotalSales() {
        return orderRepo.findByStatus("CONFIRMED")
                .stream()
                .mapToDouble(Order::getTotalAmount)
                .sum();
    }


}
