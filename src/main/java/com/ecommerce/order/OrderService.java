package com.ecommerce.order;

import com.ecommerce.common.dto.OrderItemResponseDto;
import com.ecommerce.common.dto.OrderResponseDto;
import com.ecommerce.common.dto.UpdateOrderStatusRequestDto;
import com.ecommerce.common.error.ResourceNotFoundException;
import com.ecommerce.user.AppUser;
import com.ecommerce.user.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ModelMapper modelMapper;

    @PostConstruct
    private void configureMappings() {
        modelMapper.typeMap(Order.class, OrderResponseDto.class).addMappings(mapper -> {
            mapper.map(src -> src.getCustomer().getUserId(), OrderResponseDto::setCustomerId);
            mapper.map(src -> src.getCustomer().getUsername(), OrderResponseDto::setCustomerUsername);
            mapper.map(src -> src.getAddress().getAddressId(), OrderResponseDto::setAddressId);
        });

        modelMapper.typeMap(OrderItem.class, OrderItemResponseDto.class).addMappings(mapper -> {
            mapper.map(src -> src.getProduct().getProductId(), OrderItemResponseDto::setProductId);
            mapper.map(src -> src.getProduct().getName(), OrderItemResponseDto::setProductName);
        });
    }

    @Transactional
    public Page<OrderResponseDto> getAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable).map(order -> modelMapper.map(order, OrderResponseDto.class));
    }

    @Transactional
    public OrderResponseDto getOrderById(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found for id: " + orderId));

        return modelMapper.map(order, OrderResponseDto.class);
    }

    @Transactional
    public Page<OrderResponseDto> getOrdersByCustomerId(UUID customerId, Pageable pageable) {
        AppUser user = userRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Page<Order> orders = orderRepository.findByCustomer_UserId(customerId, pageable);

        if(orders.isEmpty()) {
            throw new ResourceNotFoundException("Order not found for customer with customer id: " + customerId);
        }

        return orders.map(order -> modelMapper.map(order, OrderResponseDto.class));
    }

    @Transactional
    public OrderResponseDto updateOrderStatus(UUID orderId, UpdateOrderStatusRequestDto request) {
        Order order = orderRepository.
                findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found for id: " + orderId));

        order.setStatus(request.getStatus());

        Order updatedOrder = orderRepository.save(order);

        return modelMapper.map(updatedOrder, OrderResponseDto.class);
    }

    @Transactional
    public Map<String, Long> getUnitsSoldForProduct(UUID productId) {
        if(!productRepository.existsById(productId)) {
            throw new ResourceNotFoundException("Product not found with id: " + productId);
        }

        Long unitsSold = orderItemRepository.getTotalUnitsSoldByProductId(productId);

        return Map.of("Units Sold", unitsSold);
    }
}
