package com.example.jewellery.demo.controller;



import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.jewellery.demo.dto.InventoryResponseDto;
import com.example.jewellery.demo.dto.LowStockAlertDto;
import com.example.jewellery.demo.dto.PlaceOrderRequestDto;
import com.example.jewellery.demo.dto.UpdateStockRequestDto;
import com.example.jewellery.demo.service.InventoryService;


@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    // ✅ Update stock quantity
    @PutMapping("/{productId}/stock")
    public ResponseEntity<InventoryResponseDto> updateStock(
            @PathVariable String productId,
            @RequestBody UpdateStockRequestDto request) {

        return ResponseEntity.ok(
                inventoryService.updateStock(productId, request.getQuantity())
        );
    }

    // ✅ Get inventory by product
    @GetMapping("/{productId}")
    public ResponseEntity<InventoryResponseDto> getInventory(
            @PathVariable String productId) {

        return ResponseEntity.ok(
                inventoryService.getInventoryByProductId(productId)
        );
    }

    // ✅ Get all inventories
    @GetMapping
    public ResponseEntity<List<InventoryResponseDto>> getAllInventories() {
        return ResponseEntity.ok(inventoryService.getAllInventories());
    }

    // ✅ Available products
    @GetMapping("/available")
    public ResponseEntity<List<InventoryResponseDto>> getAvailableProducts() {
        return ResponseEntity.ok(inventoryService.getAvailableProducts());
    }

    // ✅ Out of stock products
    @GetMapping("/out-of-stock")
    public ResponseEntity<List<InventoryResponseDto>> getOutOfStockProducts() {
        return ResponseEntity.ok(inventoryService.getOutOfStockProducts());
    }
    
    // ✅ Call this AFTER order is placed successfully
    @PostMapping("/deduct-stock")
    public ResponseEntity<String> deductStock(
            @RequestBody PlaceOrderRequestDto request) {
        
    	 System.out.println("DEDUCT STOCK API CALLED");
        inventoryService.deductStockAfterOrder(request);
        return ResponseEntity.ok("Stock updated successfully");
    }

    // ✅ Low stock alert for admin
    @GetMapping("/low-stock")
    public ResponseEntity<List<LowStockAlertDto>> getLowStockAlerts() {
        return ResponseEntity.ok(inventoryService.getLowStockProducts());
    }
}
