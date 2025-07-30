package com.onlinestore.repository;

import com.onlinestore.entity.Order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByDeletedFalse();

    Optional<Order> findByIdAndDeletedFalse(Long id);

    List<Order> findByCustomerIdAndDeletedFalse(Long customerId);

    @Modifying
    @Query(
            "UPDATE `Order` o SET o.deleted = true, o.deletedBy = :deletedBy, o.deletedAt = cast(CURRENT_TIMESTAMP AS INSTANT) WHERE o.customer.id = :customerId")
    void softDeleteByCustomerId(@Param("customerId") Long customerId, @Param("deletedBy") String deletedBy);

    @Query("SELECT o.id FROM `Order` o JOIN o.products p WHERE p.id = :productId AND o.deleted = false")
    List<Long> findOrderIdsByProductId(@Param("productId") Long productId);
}
