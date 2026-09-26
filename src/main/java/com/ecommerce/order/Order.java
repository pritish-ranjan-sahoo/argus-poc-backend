package com.ecommerce.order;

import com.ecommerce.user.AppUser;
import com.ecommerce.address.Address;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.*;

@Entity
@Getter
@Setter
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID orderId;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    @NotNull(message = "Order must belong to a customer")
    @ToString.Exclude
    private AppUser customer;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    @NotEmpty(message = "An order must contain atleast one item")
    @ToString.Exclude
    private Set<OrderItem> orderItems = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "Order status is required")
    private StatusType status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "Payment method is required")
    private PaymentType paymentMethod;

    @ManyToOne
    @JoinColumn(name = "addressId", nullable = false)
    @NotNull(message = "Shipping address is required")
    private Address address;
}
