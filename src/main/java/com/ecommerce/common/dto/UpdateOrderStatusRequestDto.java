package com.ecommerce.common.dto;

import com.ecommerce.order.StatusType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateOrderStatusRequestDto {

    @NotNull(message = "Order status is required")
    private StatusType status;
}
