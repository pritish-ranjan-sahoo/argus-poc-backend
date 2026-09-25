package com.ecommerce.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartResponseDto {

    private UUID cartId;

    private UUID customerId;

    private Set<CartItemResponseDto> cartItems = new HashSet<>();
}
