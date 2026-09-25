package com.ecommerce.common.dto;

import com.ecommerce.order.PaymentType;
import com.ecommerce.order.StatusType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDto {

    private UUID orderId;

    private UUID customerId;

    private String customerUsername;

    private StatusType status;

    private PaymentType paymentMethod;

    private UUID addressId;

    private Set<OrderItemResponseDto> orderItems = new HashSet<>();
}
