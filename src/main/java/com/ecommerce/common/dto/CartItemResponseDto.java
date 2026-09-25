package com.ecommerce.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemResponseDto {

    private UUID cartItemId;

    private UUID productId;

    private String productName;

    private Integer quantity;
}
