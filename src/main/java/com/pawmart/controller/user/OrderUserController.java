package com.pawmart.controller.user;

import com.pawmart.DTO.Order.OrderRequest;
import com.pawmart.DTO.Order.OrderResponse;
import com.pawmart.exception.AppException;
import com.pawmart.repository.UserRepository;
import com.pawmart.security.JwtService;
import com.pawmart.service.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/orders")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('USER','ADMIN')")
@Tag(name = "User - Order")
public class OrderUserController {
    private final OrderService orderService;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    private Long getUserId(String authHeader) {
        String token = authHeader.substring(7);
        String email = jwtService.extractEmail(token);
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "User not found")).getId();
    }

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader, @RequestBody OrderRequest request) {
        return ResponseEntity.ok(orderService.createOrder(getUserId(authHeader), request));
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getMyOrders(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {

        return ResponseEntity.ok(orderService.getMyOrders(getUserId(authHeader)));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderById(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader, @PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.getOrderById(getUserId(authHeader), orderId));
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<OrderResponse> cancelOrder(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,@PathVariable Long id) {
        return ResponseEntity.ok(orderService.cancelOrder(getUserId(authHeader),id));
    }
}

