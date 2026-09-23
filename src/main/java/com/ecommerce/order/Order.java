package com.ecommerce.order;

import jakarta.persistence.*;
import lombok.Data;

import java.util.*;

@Entity
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID orderId;

    @ManyToOne
    @JoinColumn(name = "customerId", nullable = false)
    private AppUser customer;

    @OneToMany(mappedBy = "order")
    private Set<OrderItem> orderItems = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusType status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentType paymentMethod;

    @ManyToOne
    @JoinColumn(name = "addressId", nullable = false)
    private Address address;
}
