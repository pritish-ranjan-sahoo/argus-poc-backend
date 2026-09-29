package com.ecommerce.common.dto;

import com.ecommerce.order.PaymentType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDto {

    @NotNull(message = "Address id is required")
    private UUID addressId;

    @NotNull(message = "Payment method is required")
    private PaymentType paymentMethod;
}
