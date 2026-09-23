package com.ecommerce.common.dto;
import com.ecommerce.product.Category;
import com.ecommerce.user.AppUser;
import java.math.BigDecimal;

public class ProductRequestDto {
    private String name;
    private String description;
    private BigDecimal priceperunit;
    private Long stock;
    private Category category;
    private AppUser seller;
    private String productImageUrl;
}