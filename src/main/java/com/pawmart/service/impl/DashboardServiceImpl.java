package com.pawmart.service.impl;

import com.pawmart.DTO.Dashboard.DashboardResponse;
import com.pawmart.enums.OrderStatus;
import com.pawmart.repository.CategoryRepository;
import com.pawmart.repository.OrderRepository;
import com.pawmart.repository.ProductRepository;
import com.pawmart.repository.UserRepository;
import com.pawmart.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final OrderRepository orderRepository;

    @Override
    public DashboardResponse getDashboard() {

        DashboardResponse response = new DashboardResponse();

        response.setTotalUsers(userRepository.count());

        response.setTotalCategories(categoryRepository.count());

        response.setTotalProducts(productRepository.count());

        response.setTotalOrders(orderRepository.count());

        response.setPendingOrders(
                orderRepository.countByStatus(OrderStatus.PENDING)
        );

        response.setCompletedOrders(
                orderRepository.countByStatus(OrderStatus.DELIVERED)
        );

        response.setCancelledOrders(
                orderRepository.countByStatus(OrderStatus.CANCELLED)
        );

        response.setTotalRevenue(
                orderRepository.getTotalRevenue()
        );

        return response;
    }
}
