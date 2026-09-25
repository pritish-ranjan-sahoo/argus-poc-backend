package com.ecommerce.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, UUID> {

    @Query("SELECT COALESCE(SUM(oi.quantity), 0) FROM OrderItem oi WHERE oi.product.productId = :productId")
    Long getTotalUnitsSoldByProductId(@Param("productId") UUID productId);
}
