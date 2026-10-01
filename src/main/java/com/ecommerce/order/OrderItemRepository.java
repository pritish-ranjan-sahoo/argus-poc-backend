package com.ecommerce.order;

import com.ecommerce.common.dto.CategorySalesProjection;
import com.ecommerce.common.dto.ProductSalesByCategoryProjection;
import com.ecommerce.product.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, UUID> {

    @Query("SELECT COALESCE(SUM(oi.quantity), 0) FROM OrderItem oi WHERE oi.product.productId = :productId")
    Long getTotalUnitsSoldByProductId(@Param("productId") UUID productId);

    @Query("SELECT COALESCE(SUM(oi.price * oi.quantity), 0) FROM OrderItem oi ")
    Double getTotalSales();

    @Query(value = "SELECT oi.product_id FROM order_item oi GROUP BY oi.product_id ORDER BY SUM(oi.quantity) DESC LIMIT 1", nativeQuery = true)
    Optional<UUID> getMostSoldProduct();

    @Query("SELECT oi.product.categoryType AS category, COALESCE(SUM(oi.price * oi.quantity), 0) AS totalSales FROM OrderItem oi GROUP BY oi.product.categoryType")
    List<CategorySalesProjection> getTotalSalesByCategory();

    @Query("SELECT oi.product.productId AS productId, SUM(oi.quantity) AS totalQuantity FROM OrderItem oi WHERE oi.product.categoryType = :category GROUP BY oi.product.productId ORDER BY SUM(oi.quantity) DESC")
    List<ProductSalesByCategoryProjection> topProductByCategory(@Param("category") CategoryType category);
}
