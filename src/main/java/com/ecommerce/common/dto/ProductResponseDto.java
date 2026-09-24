package com.ecommerce.common.dto;
import com.ecommerce.product.CategoryType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDto {
    private UUID productId;
    private String name;
    private String description;
    private BigDecimal pricePerUnit;
    private Long stock;
    private CategoryType categoryType;
    private String productImageUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID sellerId;
}
