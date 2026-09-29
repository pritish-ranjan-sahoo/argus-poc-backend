package com.ecommerce.order;

import com.ecommerce.address.Address;
import com.ecommerce.address.AddressRepository;
import com.ecommerce.cart.Cart;
import com.ecommerce.cart.CartItem;
import com.ecommerce.cart.CartRepository;
import com.ecommerce.common.dto.OrderItemResponseDto;
import com.ecommerce.common.dto.OrderRequestDto;
import com.ecommerce.common.dto.OrderResponseDto;
import com.ecommerce.common.dto.UpdateOrderStatusRequestDto;
import com.ecommerce.common.error.InsufficientStockException;
import com.ecommerce.common.error.ResourceNotFoundException;
import com.ecommerce.product.Product;
import com.ecommerce.product.ProductRepository;
import com.ecommerce.user.AppUser;
import com.ecommerce.user.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ModelMapper modelMapper;

//    @PostConstruct
//    private void configureMappings() {
//        modelMapper.typeMap(Order.class, OrderResponseDto.class).addMappings(mapper -> {
//            mapper.map(src -> src.getCustomer().getId(), OrderResponseDto::setCustomerId);
//            mapper.map(src -> src.getCustomer().getUsername(), OrderResponseDto::setCustomerUsername);
//            mapper.map(src -> src.getAddress().getAddressId(), OrderResponseDto::setAddressId);
//        });
//
//        modelMapper.typeMap(OrderItem.class, OrderItemResponseDto.class).addMappings(mapper -> {
//            mapper.map(src -> src.getProduct().getProductId(), OrderItemResponseDto::setProductId);
//            mapper.map(src -> src.getProduct().getName(), OrderItemResponseDto::setProductName);
//        });
//    }


    @PostConstruct
    private void configureMappings() {

        // Must be set before any createTypeMap() call, since ambiguity is
        // checked during implicit-mapping construction, not in addMappings()
        modelMapper.getConfiguration().setAmbiguityIgnored(true);

        if (modelMapper.getTypeMap(Order.class, OrderResponseDto.class) == null) {
            modelMapper.createTypeMap(Order.class, OrderResponseDto.class).addMappings(mapper -> {
                mapper.map(src -> src.getCustomer().getId(), OrderResponseDto::setCustomerId);
                mapper.map(src -> src.getCustomer().getUsername(), OrderResponseDto::setCustomerUsername);
                mapper.map(src -> src.getAddress().getAddressId(), OrderResponseDto::setAddressId);
            });
        }

        if (modelMapper.getTypeMap(OrderItem.class, OrderItemResponseDto.class) == null) {
            modelMapper.createTypeMap(OrderItem.class, OrderItemResponseDto.class).addMappings(mapper -> {
                mapper.map(src -> src.getProduct().getProductId(), OrderItemResponseDto::setProductId);
                mapper.map(src -> src.getProduct().getName(), OrderItemResponseDto::setProductName);
            });
        }
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
    public OrderResponseDto createNewOrder(UUID customerId, OrderRequestDto request) {
        AppUser customer = userRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with customer id: " + customerId));

        Cart cart = cartRepository.findByUser_Id(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for customer with id: " + customerId));

        if(cart.getCartItems().isEmpty()) {
            throw new ResourceNotFoundException("Cannot create order from empty cart");
        }

        Address address = addressRepository.findById(request.getAddressId())
                .orElseThrow(() -> new ResourceNotFoundException("Address not found for address id: " + request.getAddressId()));

        for (CartItem cartItem : cart.getCartItems()) {
            Product product = cartItem.getProduct();
            if (product.getStock() < cartItem.getQuantity()) {
                throw new InsufficientStockException("Insufficient stock for product with id: " + product.getProductId());
            }
        }

        Order order = new Order();
        order.setCustomer(customer);
        order.setAddress(address);
        order.setPaymentMethod(request.getPaymentMethod());
        order.setStatus(StatusType.PENDING);

        Set<OrderItem> orderItems = new HashSet<>();

        for (CartItem cartItem : cart.getCartItems()) {
            Product product = cartItem.getProduct();

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(product.getPricePerUnit());
            orderItems.add(orderItem);

            product.setStock(product.getStock() - cartItem.getQuantity());
            productRepository.save(product);
        }

        order.setOrderItems(orderItems);

        Order savedOrder = orderRepository.save(order);

        cart.getCartItems().clear();
        cartRepository.save(cart);

        return modelMapper.map(savedOrder, OrderResponseDto.class);
    }

    @Transactional
    public Page<OrderResponseDto> getOrdersByCustomerId(UUID customerId, Pageable pageable) {
        AppUser user = userRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Page<Order> orders = orderRepository.findByCustomer_Id(customerId, pageable);

        if(orders.isEmpty()) {
            throw new ResourceNotFoundException("Order not found for customer with customer id: " + customerId);
        }

        return orders.map(order -> modelMapper.map(order, OrderResponseDto.class));
    }

    @Transactional
    public OrderResponseDto updateOrderStatus(UUID orderId, UpdateOrderStatusRequestDto request) {

        Order order = orderRepository.
                findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found for id: " + orderId));

        Optional<AppUser> user = userRepository.findById(order.getCustomer().getId());

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
