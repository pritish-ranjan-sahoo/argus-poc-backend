package com.ecommerce.product;

import com.ecommerce.user.AppUser;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="products")
public class Product{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID productId;
    @Size(min=3,max=50,message="Name must be between 3 and 50 characters")
    @Column(nullable=false)
    private String name;
    @Size(min=3,max=50,message="Description must be between 3 and 50 characters")
    private String description;
    @Column(nullable=false)
    @Min(value=0,message="Price cant be negative")
    private BigDecimal pricePerUnit;
    @Column(nullable=false)
    @Min(value=0,message="Stock cant be negative")
    private Long stock;
    @Enumerated(EnumType.STRING)
    private CategoryType categoryType;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="seller_id",nullable=false)
    private AppUser seller;
    private String productImageUrl;
}