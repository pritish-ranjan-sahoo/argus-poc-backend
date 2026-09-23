package com.ecommerce.common.dto;

import com.ecommerce.product.CategoryType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDto {
    private UUID id;
    private String name;
    private String description;
    private BigDecimal priceperunit;
    private Long stock;
    private CategoryType categoryType;
    private String productImageUrl;
    private Date createdAt;
    private Date updatedAt;
    private UUID sellerId;
}
