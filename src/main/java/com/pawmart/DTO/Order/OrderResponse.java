package com.pawmart.DTO.Order;

import com.pawmart.enums.OrderStatus;
import com.pawmart.enums.PaymentStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class OrderResponse {
    private Long id;

    private BigDecimal totalPrice;

    private String paymentMethod;

    private PaymentStatus paymentStatus;

    private OrderStatus status;

    private String note;

    private LocalDateTime createdAt;

    private List<OrderItemResponse> items;
}
