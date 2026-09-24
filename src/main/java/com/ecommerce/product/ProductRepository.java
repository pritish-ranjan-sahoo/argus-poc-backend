package com.ecommerce.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    Optional<Product> findByProductId(UUID id);
    Page<Product> findBySellerId(UUID sellerId, Pageable pageable);
    Page<Product> findByCategoryType(CategoryType categoryType,Pageable pageable);
}
