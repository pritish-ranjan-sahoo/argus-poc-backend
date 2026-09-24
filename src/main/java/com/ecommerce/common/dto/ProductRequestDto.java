package com.ecommerce.common.dto;

import com.ecommerce.product.CategoryType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDto {

    @NotBlank(message = "Product name is required")
    @Size(min = 2, max = 100, message = "Product name must be between 2 and 100 characters")
    private String name;

    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    private String description;

    @NotNull(message = "Price per unit is required")
    @DecimalMin(value = "0.1", message = "Price must be greater than or equal to 0.01")
    private BigDecimal pricePerUnit;

    @NotNull(message = "Stock quantity is required")
    @Min(value = 0, message = "Stock cannot be negative")
    private Long stock;

    @NotNull(message = "Category type is required")
    private CategoryType categoryType;

    @NotNull(message = "Seller ID is required")
    private UUID sellerId;

    private String productImageUrl;
}
