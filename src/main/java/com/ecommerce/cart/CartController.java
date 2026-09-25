package com.ecommerce.cart;

import com.ecommerce.common.dto.CartItemRequestDto;
import com.ecommerce.common.dto.CartResponseDto;
import com.ecommerce.common.dto.UpdateCartItemRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/carts")
@Tag(name = "Cart Management", description = "APIs for managing customer shopping carts")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/customer/{customerId}")
    @Operation(summary = "Get cart by customer ID", description = "Retrieves the shopping cart of a active customer")
    public ResponseEntity<CartResponseDto> getCartByCustomerId(@PathVariable UUID customerId) {
        CartResponseDto cart = cartService.getCartByCustomerId(customerId);
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/customer/{customerId}/items")
    @Operation(summary = "Add item to cart", description = "Adds a product to the customer's shopping cart")
    public ResponseEntity<CartResponseDto> addProductToCart(@PathVariable UUID customerId, @Valid @RequestBody CartItemRequestDto request) {
        CartResponseDto cart = cartService.addProductToCart(customerId, request);
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/customer/{customerId}/items/{productId}")
    @Operation(summary = "Remove item from cart", description = "Removes a specific product entirely from customer's cart")
    public ResponseEntity<CartResponseDto> removeProductFromCart(@PathVariable UUID customerId, @PathVariable UUID productId) {
        CartResponseDto cart = cartService.removeProductFromCart(customerId, productId);
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("customer/{customerId}")
    @Operation(summary = "Delete cart", description = "Deletes the entire shopping cart of a customer")
    public ResponseEntity<Map<String, String>> deleteCart(@PathVariable UUID customerId) {
        cartService.deleteCart(customerId);
        return ResponseEntity.ok(Map.of("message", "Cart deleted successfully"));
    }

    @PatchMapping("customer/{customerId}/items/{productId}")
    @Operation(summary = "Update item quantity", description = "Updates the quantity of an existing item in the cart")
    public ResponseEntity<CartResponseDto> updateProductQuantity(@PathVariable UUID customerId, @PathVariable UUID productId, @Valid @RequestBody UpdateCartItemRequestDto request) {
        CartResponseDto cart = cartService.updateCartQuantity(customerId, productId, request);
        return ResponseEntity.ok(cart);
    }
}
