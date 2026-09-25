package com.ecommerce.common.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateCartItemRequestDto {

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be atlest one")
    private Integer quantity;
}
