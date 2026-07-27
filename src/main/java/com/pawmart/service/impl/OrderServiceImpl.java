package com.pawmart.service.impl;

import com.pawmart.DTO.Order.OrderItemResponse;
import com.pawmart.DTO.Order.OrderRequest;
import com.pawmart.DTO.Order.OrderResponse;
import com.pawmart.entity.*;
import com.pawmart.enums.OrderStatus;
import com.pawmart.enums.PaymentStatus;
import com.pawmart.exception.AppException;
import com.pawmart.repository.*;
import com.pawmart.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final ProductRepository productRepository;

    private OrderResponse toResponse(Order order) {

        OrderResponse response = new OrderResponse();
        response.setId(order.getId());
        response.setTotalPrice(order.getTotalPrice());
        response.setPaymentMethod(order.getPaymentMethod());
        response.setPaymentStatus(order.getPaymentStatus());
        response.setStatus(order.getStatus());
        response.setNote(order.getNote());
        response.setCreatedAt(order.getCreatedAt());

        response.setItems(
                order.getOrderItems()
                        .stream()
                        .map(item -> {

                            OrderItemResponse dto = new OrderItemResponse();

                            dto.setProductId(item.getProduct().getId());
                            dto.setProductName(item.getProduct().getName());
                            dto.setPrice(item.getPrice());
                            dto.setQuantity(item.getQuantity());
                            dto.setSubtotal(item.getSubtotal());

                            return dto;

                        })
                        .toList()
        );
        return response;
    }

    @Override
    @Transactional
    public OrderResponse createOrder(Long userId, OrderRequest request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "User not found"));

        Address address = addressRepository
                .findByIdAndUserId(request.getAddressId(), userId)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Address not found"));

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new AppException(HttpStatus.BAD_REQUEST, "Cart is empty"));

        if (cart.getCartItems().isEmpty()) {
            throw new AppException(HttpStatus.BAD_REQUEST, "Cart is empty");
        }

        BigDecimal totalPrice = BigDecimal.ZERO;

        Order order = new Order();

        order.setUser(user);
        order.setAddress(address);
        order.setPaymentMethod(request.getPaymentMethod());
        order.setPaymentStatus(PaymentStatus.UNPAID);
        order.setStatus(OrderStatus.PENDING);
        order.setNote(request.getNote());
        order.setTotalPrice(BigDecimal.ZERO);

        order = orderRepository.save(order);

        for (CartItem cartItem : cart.getCartItems()) {

            Product product = cartItem.getProduct();

            if (product.getStock() < cartItem.getQuantity()) {
                throw new AppException( HttpStatus.BAD_REQUEST,product.getName() + " is out of stock"  );
            }

            product.setStock( product.getStock() - cartItem.getQuantity());

            productRepository.save(product);

            OrderItem orderItem = new OrderItem();

            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setPrice(product.getPrice());
            orderItem.setQuantity(cartItem.getQuantity());

            BigDecimal subtotal = product.getPrice()
                    .multiply(BigDecimal.valueOf(cartItem.getQuantity()));

            orderItem.setSubtotal(subtotal);

            totalPrice = totalPrice.add(subtotal);

            orderItemRepository.save(orderItem);
        }

        order.setTotalPrice(totalPrice);

        order = orderRepository.save(order);

        cartItemRepository.deleteByCartId(cart.getId());

        return toResponse(order);
    }
    @Override
    public List<OrderResponse> getMyOrders(Long userId) {

        return orderRepository.findByUserId(userId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public OrderResponse getOrderById(Long userId,Long orderId) {

        Order order = orderRepository
                .findByIdAndUserId(orderId, userId)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND,"Order not found"));

        return toResponse(order);
    }

    @Override
    public OrderResponse updateOrderStatus(Long orderId, OrderStatus status) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND,"Order not found"));
        if(order.getStatus() == OrderStatus.CANCELLED){
            throw new AppException(HttpStatus.BAD_REQUEST,"Cancelled order cannot be updated.");
        }
        if (status == OrderStatus.CANCELLED){
            throw new AppException(HttpStatus.BAD_REQUEST,"Use cancel API to cancel order");
        }
        order.setStatus(status);

        return toResponse(orderRepository.save(order));
    }

    @Override
    public OrderResponse updatePaymentStatus(Long orderId, PaymentStatus paymentStatus) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND,"Order not found"));

        order.setPaymentStatus(paymentStatus);

        return toResponse(orderRepository.save(order));
    }

    @Override
    @Transactional
    public OrderResponse cancelOrder(Long userId, Long orderId) {

        Order order = orderRepository.findByIdAndUserId(orderId, userId)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND,"Order not found"));

        if (order.getStatus() != OrderStatus.PENDING && order.getStatus() != OrderStatus.CONFIRMED) {

            throw new AppException(HttpStatus.BAD_REQUEST,"Order cannot be cancelled");
        }

        order.setStatus(OrderStatus.CANCELLED);

        for (OrderItem item : order.getOrderItems()) {
            Product product = item.getProduct();

            product.setStock(product.getStock() + item.getQuantity());

            productRepository.save(product);
        }

        return toResponse(orderRepository.save(order));
    }

    @Override
    public Page<OrderResponse> getAllOrders(OrderStatus status, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Order> orders;

        if (status == null) {
            orders = orderRepository.findAll(pageable);
        } else {
            orders = orderRepository.findByStatus(status, pageable);
        }

        return orders.map(this::toResponse);
    }

    @Override
    public OrderResponse getOrderById(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new AppException( HttpStatus.NOT_FOUND, "Order not found"));

        return toResponse(order);
    }
}
