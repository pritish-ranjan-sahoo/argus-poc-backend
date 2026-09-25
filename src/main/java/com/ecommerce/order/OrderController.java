package com.ecommerce.order;

import com.ecommerce.common.dto.OrderResponseDto;
import com.ecommerce.common.dto.UpdateOrderStatusRequestDto;
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
}
