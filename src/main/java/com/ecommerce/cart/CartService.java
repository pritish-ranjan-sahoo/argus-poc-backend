package com.ecommerce.cart;

import com.ecommerce.common.dto.*;
import com.ecommerce.common.error.InsufficientStockException;
import com.ecommerce.common.error.ResourceNotFoundException;
import com.ecommerce.order.Order;
import com.ecommerce.order.OrderItem;
import com.ecommerce.user.AppUser;
import com.ecommerce.user.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    @PostConstruct
    private void configureMappings() {
        modelMapper.typeMap(Cart.class, CartResponseDto.class).addMappings(mapper -> {
            mapper.map(src -> src.getUser().getUserId(), CartResponseDto::setCustomerId);
        });

        modelMapper.typeMap(CartItem.class, CartItemResponseDto.class).addMappings(mapper -> {
            mapper.map(src -> src.getProduct().getProductId(), CartItemResponseDto::setProductId);
            mapper.map(src -> src.getProduct().getName(), CartItemResponseDto::setProductName);
        });
    }

    @Transactional
    public CartResponseDto getCartByCustomerId(UUID customerId) {
        Cart cart = cartRepository.findByUser_UserId(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for customer id: " + customerId));

        return modelMapper.map(cart, CartResponseDto.class);
    }

    @Transactional
    public CartResponseDto addProductToCart(UUID customerId, CartItemRequestDto request) {

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

        Cart savedCart = cartRepository.save(cart);

        return modelMapper.map(savedCart, CartResponseDto.class);
    }

    private Cart createNewCart(UUID customerId) {
        AppUser user = userRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Cart cart = new Cart();
        cart.setUser(user);
        return cart;
    }

    @Transactional
    public CartResponseDto removeProductFromCart(UUID customerId, UUID productId) {

        Cart cart = cartRepository.findByUser_UserId(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for customer id: " + customerId));

        CartItem itemToRemove = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getProductId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Product " + productId + " not found in the cart"));

        cart.getCartItems().remove(itemToRemove);

        Cart savedCart = cartRepository.save(cart);

        return modelMapper.map(savedCart, CartResponseDto.class);
    }

    @Transactional
    public void deleteCart(UUID customerId) {
        Cart cart = cartRepository.findByUser_UserId(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for customer id: " + customerId));

        cartRepository.delete(cart);
    }

    @Transactional
    public CartResponseDto updateCartQuantity(UUID customerId, UUID productId, UpdateCartItemRequestDto request) {
        Cart cart = cartRepository.findByUser_UserId(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for customer id: " + customerId));

        CartItem itemToUpdate = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getProductId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Product " + productId + " not found in the cart"));

        Product product = itemToUpdate.getProduct();

        if(product.getStock() < request.getQuantity()) {
            throw new InsufficientStockException("Product out of stock");
        }

        itemToUpdate.setQuantity(request.getQuantity());

        Cart savedCart = cartRepository.save(cart);

        return modelMapper.map(savedCart, CartResponseDto.class);
    }
}
