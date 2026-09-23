package com.ecommerce.common.dto;
import com.ecommerce.product.CategoryType;
import com.ecommerce.user.AppUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDto {
    private String name;
    private String description;
    private BigDecimal priceperunit;
    private Long stock;
    private CategoryType categoryType;
    private AppUser seller;
    private String productImageUrl;
}