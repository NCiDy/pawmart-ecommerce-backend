package com.pawmart.repository;

import com.pawmart.entity.Order;
import com.pawmart.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findByUserId(Long userId);
    Optional<Order> findByIdAndUserId(Long id,Long userId);

    Page<Order> findAll(Pageable pageable);

    Page<Order> findByStatus(OrderStatus status, Pageable pageable);

    long countByStatus(OrderStatus status);
    @Query("""
       SELECT COALESCE(SUM(o.totalPrice),0)
       FROM Order o
       WHERE o.status = com.pawmart.enums.OrderStatus.DELIVERED
       """)
    BigDecimal getTotalRevenue();
}
