package com.example.jewellery.demo.service;



import java.util.List;

import com.example.jewellery.demo.dto.InventoryResponseDto;
import com.example.jewellery.demo.dto.LowStockAlertDto;
import com.example.jewellery.demo.dto.PlaceOrderRequestDto;



public interface InventoryService {

    InventoryResponseDto updateStock(String productId, int quantity);

    InventoryResponseDto getInventoryByProductId(String productId);

    List<InventoryResponseDto> getAllInventories();

    List<InventoryResponseDto> getAvailableProducts();

    List<InventoryResponseDto> getOutOfStockProducts();
    
    void deductStockAfterOrder(PlaceOrderRequestDto request);

    List<LowStockAlertDto> getLowStockProducts();
}
