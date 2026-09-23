package com.ecommerce.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/carts")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<Cart> getCartByCustomerId(@PathVariable UUID customerId) {
        Cart cart = cartService.getCartByCustomerId(customerId);
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/customer/{customerId}/items")
    public ResponseEntity<Cart> addProductToCart(@PathVariable UUID customerId, @RequestBody CartItemRequestDto request) {
        Cart cart = cartService.addProductToCart(customerId, request);
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/customer/{customerId}/items/{productId}")
    public ResponseEntity<Cart> removeProductFromCart(@PathVariable UUID customerId, @PathVariable UUID productId) {
        Cart cart = cartService.removeProductFromCart(customerId, productId);
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("customer/{customerId}")
    public ResponseEntity<Map<String, String>> deleteCart(@PathVariable UUID customerId) {
        cartService.deleteCart(customerId);
        return ResponseEntity.ok(Map.of("message", "Cart deleted successfully"));
    }

    @PatchMapping("customer/{customerId}/items/{productId}")
    public ResponseEntity<Cart> updateProductQuantity(@PathVariable UUID customerId, @PathVariable UUID productId, @RequestBody UpdateCartItemRequestDto request) {
        Cart cart = cartService.updateCartQuantity(customerId, productId, request);
        return ResponseEntity.ok(cart);
    }
}
