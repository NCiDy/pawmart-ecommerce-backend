package com.pawmart.service;

import com.pawmart.DTO.Order.OrderRequest;
import com.pawmart.DTO.Order.OrderResponse;
import com.pawmart.enums.OrderStatus;
import com.pawmart.enums.PaymentStatus;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrderService {
    OrderResponse createOrder( Long userId, OrderRequest request);

    List<OrderResponse> getMyOrders(Long userId);

    OrderResponse getOrderById(Long userId,Long orderId);

    OrderResponse updateOrderStatus(Long orderId, OrderStatus status);

    OrderResponse updatePaymentStatus(Long orderId, PaymentStatus paymentStatus);

    OrderResponse cancelOrder(Long userId,Long orderId);

    Page<OrderResponse> getAllOrders(OrderStatus status, int page, int size);

    OrderResponse getOrderById(Long orderId);
}
