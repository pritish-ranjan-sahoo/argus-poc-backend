package com.ecommerce.cart;

import com.ecommerce.common.error.InsufficientStockException;
import com.ecommerce.common.error.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Transactional
    public Cart getCartByCustomerId(UUID customerId) {
        return cartRepository.findByUser_UserId(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for customer id: " + customerId));
    }

    @Transactional
    public Cart addProductToCart(UUID customerId, CartItemRequestDto request) {

        Cart cart = cartRepository.findByUser_UserId(customerId).orElseGet(() -> createNewCart(customerId));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        CartItem existingItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getProductId().equals(product.getProductId()))
                .findFirst()
                .orElse(null);

        int requestedTotalQuantity = request.getQuantity() + (existingItem != null ? existingItem.getQuantity() : 0);

        if(product.getStock() < requestedTotalQuantity) {
            throw new InsufficientStockException("Product out of stock");
        }

        if(existingItem != null) {
            existingItem.setQuantity(requestedTotalQuantity);
        }
        else {
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(request.getQuantity());
            cart.getCartItems().add(newItem);
        }

        return cartRepository.save(cart);
    }

    private Cart createNewCart(UUID customerId) {
        AppUser user = AppUserRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Cart cart = new Cart();
        cart.setUser(user);
        return cart;
    }

    @Transactional
    public Cart removeProductFromCart(UUID customerId, UUID productId) {

        Cart cart = cartRepository.findByUser_UserId(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for customer id: " + customerId));

        CartItem itemToRemove = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getProductId.equals(productId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Product " + productId + " not found in the cart"));

        cart.getCartItems().remove(itemToRemove);

        return cartRepository.save(cart);
    }

    @Transactional
    public void deleteCart(UUID customerId) {
        Cart cart = cartRepository.findByUser_UserId(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for customer id: " + customerId));

        cartRepository.delete(cart);
    }

    @Transactional
    public Cart updateCartQuantity(UUID customerId, UUID productId, UpdateCartItemRequestDto request) {
        Cart cart = cartRepository.findByUser_UserId(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for customer id: " + customerId));

        CartItem itemToUpdate = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getProductId.equals(productId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Product " + productId + " not found in the cart"));

        Product product = itemToUpdate.getProduct();

        if(product.getStock() < request.getQuantity()) {
            throw new InsufficientStockException("Product out of stock");
        }

        itemToUpdate.setQuantity(request.getQuantity());

        return cartRepository.save(cart);
    }
}
