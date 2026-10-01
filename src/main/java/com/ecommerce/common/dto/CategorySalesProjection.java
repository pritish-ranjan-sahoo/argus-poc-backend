package com.ecommerce.common.dto;

import com.ecommerce.product.CategoryType;

public interface CategorySalesProjection {
    CategoryType getCategory();

    Double getTotalSales();
}
