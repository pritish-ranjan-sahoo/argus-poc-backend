package com.ecommerce.cart;

import lombok.Data;

import java.util.UUID;

@Data
public class CartItemRequestDto {

    private UUID productId;

    private Integer quantity;
}
