package com.ecommerce.common.dto;

import java.util.UUID;

public interface ProductSalesByCategoryProjection {
    UUID getProductId();

    Long getTotalQuantity();
}
