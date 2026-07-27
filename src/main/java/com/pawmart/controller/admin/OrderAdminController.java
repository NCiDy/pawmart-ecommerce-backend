package com.pawmart.controller.admin;

import com.pawmart.DTO.Order.OrderResponse;
import com.pawmart.DTO.Order.UpdateOrderStatusRequest;
import com.pawmart.DTO.Order.UpdatePaymentStatusRequest;
import com.pawmart.enums.OrderStatus;
import com.pawmart.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/orders")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class OrderAdminController {
    private final OrderService orderService;

    @PutMapping("/{id}/status")
    public ResponseEntity<OrderResponse> updateStatus(@Valid @PathVariable Long id, @RequestBody UpdateOrderStatusRequest request) {
        return ResponseEntity.ok( orderService.updateOrderStatus(id,request.getStatus()));
    }

    @PutMapping("/{id}/payment-status")
    public ResponseEntity<OrderResponse> updatePaymentStatus(@Valid @PathVariable Long id,@RequestBody UpdatePaymentStatusRequest request) {

        return ResponseEntity.ok(orderService.updatePaymentStatus(id, request.getPaymentStatus()));
    }

    @GetMapping
    public ResponseEntity<Page<OrderResponse>> getAllOrders(@RequestParam(required = false) OrderStatus status, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok( orderService.getAllOrders(status,page,size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }
}
