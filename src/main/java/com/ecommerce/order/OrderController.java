package com.ecommerce.order;

import com.ecommerce.common.dto.*;
import com.ecommerce.product.CategoryType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
@Tag(name = "Order Management", description = "APIs for managing orders placed by customers")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    @Operation(summary = "Get all orders", description = "Retrieves a paginated list of all the orders in the system")
    public ResponseEntity<Page<OrderResponseDto>> getAllOrders(@RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "20") int size,
                                                               @RequestParam(defaultValue = "orderId") String attribute) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(attribute).ascending());
        Page<OrderResponseDto> orders = orderService.getAllOrders(pageable);
        return ResponseEntity.ok(orders);
    }

    @PostMapping("/customer/{customerId}")
    @Operation(summary = "Place an order", description = "Customer can place a new order from their existing cart")
    public ResponseEntity<OrderResponseDto> createNewOrder(@PathVariable UUID customerId, @Valid @RequestBody OrderRequestDto request) {
        OrderResponseDto order = orderService.createNewOrder(customerId, request);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/{orderId}")
    @Operation(summary = "Get order by order ID", description = "Retrieves the details of a specific order")
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable UUID orderId) {
        OrderResponseDto order = orderService.getOrderById(orderId);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/customer/{customerId}")
    @Operation(summary = "Get orders by customer id", description = "Retrieves a paginated list of all orders placed by a specific customer")
    public ResponseEntity<Page<OrderResponseDto>> getOrdersByCustomerId(@PathVariable UUID customerId,
                                                                        @RequestParam(defaultValue = "0") int page,
                                                                        @RequestParam(defaultValue = "20") int size,
                                                                        @RequestParam(defaultValue = "orderId") String attribute) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(attribute).ascending());
        Page<OrderResponseDto> orders = orderService.getOrdersByCustomerId(customerId, pageable);
        return ResponseEntity.ok(orders);
    }

    @PatchMapping("/{orderId}/status")
    @Operation(summary = "Update order status", description = "Updates the status of an order ")
    public ResponseEntity<OrderResponseDto> updateOrderStatus(@PathVariable UUID orderId, @Valid @RequestBody UpdateOrderStatusRequestDto request) {
        OrderResponseDto order = orderService.updateOrderStatus(orderId, request);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/product/{productId}")
    @Operation(summary = "Get total units sold for a product", description = "Returns the aggregated count of total quantities sold for a given product across all orders")
    public ResponseEntity<Map<String, Long>> getUnitsSoldForProduct(@PathVariable UUID productId) {
        Map<String, Long> response = orderService.getUnitsSoldForProduct(productId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/sales/total")
    @Operation(summary = "Get total sales/revenue", description = "Calculates lifetime gross sales value across all completed orders")
    public ResponseEntity<Map<String, Double>> getTotalSales(){
        Map<String, Double> response = orderService.getTotalSales();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/products/most-sold")
    @Operation(summary = "Get overall most sold product", description = "Retrieves details of the most sold product across the entire platform")
    public ResponseEntity<ProductResponseDto> getMostSoldProduct() {
        ProductResponseDto product = orderService.getMostSoldProduct();
        return ResponseEntity.ok(product);
    }

    @GetMapping("/sales/category")
    @Operation(summary = "Get sales revenue by category", description = "Breaks down total revenue generated grouped by product category")
    public ResponseEntity<Map<CategoryType, Double>> getTotalSalesByCategory() {
        Map<CategoryType, Double> response = orderService.getTotalSalesByCategory();
        return ResponseEntity.ok(response);
    }

    @GetMapping("products/most-sold/category")
    @Operation(summary = "Get top products for each category", description = "Retrieves details of most sold product in each category")
    public ResponseEntity<Map<CategoryType, List<ProductResponseDto>>> topProductByCategory() {
        Map<CategoryType, List<ProductResponseDto>> response = orderService.topProductByCategory();
        return ResponseEntity.ok(response);
    }

    @GetMapping("topsellers")
    @Operation(summary = "Get top 3 sellers", description = "Retrieves details of top 3 sellers across the platform")
    public ResponseEntity<List<UserResponseDTO>> getTopSellers() {
        List<UserResponseDTO> response = orderService.getTopSellers();
        return ResponseEntity.ok(response);
    }
}
