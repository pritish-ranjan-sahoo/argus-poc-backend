package com.ecommerce.order;

import com.ecommerce.product.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Getter
@Setter
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID orderItemId;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @NotNull(message = "Order item must belong to an order")
    @ToString.Exclude
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @NotNull(message = "Order item must reference a valid product")
    @ToString.Exclude
    private Product product;

    @Min(value = 1, message = "Quantity cannot be 0")
    @Column(nullable = false)
    @NotNull(message = "Quantity is required")
    private Integer quantity;

    @Min(value = 0, message = "Price cannot be negative")
    @Column(nullable = false)
    @NotNull(message = "Price is required")
    private BigDecimal price;
}
