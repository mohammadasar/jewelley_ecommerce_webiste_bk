package com.example.jewellery.demo.service;



import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.jewellery.demo.dto.InventoryResponseDto;
import com.example.jewellery.demo.dto.LowStockAlertDto;
import com.example.jewellery.demo.dto.OrderItemDto;
import com.example.jewellery.demo.dto.PlaceOrderRequestDto;
import com.example.jewellery.demo.model.Product;
import com.example.jewellery.demo.repository.ProductRepository;
import com.example.jewellery.demo.util.InventoryConstants;



@Service
public class InventoryServiceImpl implements InventoryService {

    private final ProductRepository productRepository;

    public InventoryServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public InventoryResponseDto updateStock(String productId, int quantity) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setQuantity(quantity);
        product.setInStock(quantity > 0);

        productRepository.save(product);

        return mapToDto(product);
    }

    @Override
    public InventoryResponseDto getInventoryByProductId(String productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        return mapToDto(product);
    }

    @Override
    public List<InventoryResponseDto> getAllInventories() {

        return productRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<InventoryResponseDto> getAvailableProducts() {

        return productRepository.findByInStock(true)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<InventoryResponseDto> getOutOfStockProducts() {

        return productRepository.findByInStock(false)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private InventoryResponseDto mapToDto(Product product) {
        return new InventoryResponseDto(
                product.getId(),
                product.getProductName(),
                product.isInStock(),
                product.getQuantity()
        );
    }
    
    // ✅ AUTO STOCK DEDUCTION AFTER ORDER
    @Override
    
    public void deductStockAfterOrder(PlaceOrderRequestDto request) {
    	
    	 System.out.println("ORDER ITEMS SIZE: " + request.getItems().size());

        for (OrderItemDto item : request.getItems()) {

            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            if (product.getQuantity() < item.getQuantity()) {
                throw new RuntimeException(
                        "Insufficient stock for product: " + product.getProductName());
            }

            int updatedQty = product.getQuantity() - item.getQuantity();
            product.setQuantity(updatedQty);
            product.setInStock(updatedQty > 0);

            productRepository.save(product);
            System.out.println("UPDATED QUANTITY: " + updatedQty);
        }
    }

    // ✅ LOW STOCK ALERT
    @Override
    public List<LowStockAlertDto> getLowStockProducts() {

        return productRepository
                .findByQuantityLessThan(InventoryConstants.LOW_STOCK_THRESHOLD)
                .stream()
                .map(p -> new LowStockAlertDto(
                        p.getId(),
                        p.getProductName(),
                        p.getQuantity()))
                .collect(Collectors.toList());
    }
}
