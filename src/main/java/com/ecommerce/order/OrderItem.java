package com.ecommerce.order;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Data
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID orderItemId;

    @ManyToOne
    @JoinColumn(name = "orderId")
    @ToString.Exclude
    private Order order;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;

    @Min(value = 1, message = "Quantity cannot be 0")
    @Column(nullable = false)
    private Integer quantity;

    @Min(value = 0, message = "Price cannot be negative")
    @Column(nullable = false)
    private BigDecimal price;
}
