package com.ecommerce.common.dto;

import com.ecommerce.order.PaymentType;
import com.ecommerce.order.StatusType;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class SellerOrderResponseDto {
    private UUID orderId;
    private StatusType status;
    private PaymentType paymentMethod;
    private UUID orderItemId;
    private UUID productId;
    private String productName;
    private BigDecimal pricePerUnit;
    private Integer units;
    private BigDecimal lineTotal;
}