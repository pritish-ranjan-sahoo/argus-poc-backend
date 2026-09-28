package com.ecommerce.cart;

import com.ecommerce.product.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Entity
@Getter
@Setter
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID cartItemId;

    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    @NotNull(message = "Cart item must belong to a cart")
    @ToString.Exclude
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    @NotNull(message = "Cart item must reference a valid product")
    private Product product;

    @Min(value = 1, message = "Quantity cannot be 0")
    @Column(nullable = false)
    @NotNull(message = "Quantity cannot be null")
    private Integer quantity;
}