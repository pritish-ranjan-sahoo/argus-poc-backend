package com.ecommerce.common.dto;

import com.ecommerce.product.Category;
import com.ecommerce.user.AppUser;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public class ProductResponseDto {
    private UUID id;
    private String name;
    private String description;
    private BigDecimal priceperunit;
    private Long stock;
    private Category category;
    private AppUser seller;
    private String productImageUrl;
    private Date createdAt;
    private Date updatedAt;
    private Long sellerId;
}
