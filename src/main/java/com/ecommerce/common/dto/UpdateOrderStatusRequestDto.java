package com.ecommerce.common.dto;

import com.ecommerce.order.StatusType;
import lombok.Data;

@Data
public class UpdateOrderStatusRequestDto {

    private StatusType status;
}
