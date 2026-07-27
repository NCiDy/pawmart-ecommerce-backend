package com.pawmart.DTO.Dashboard;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class DashboardResponse {
    private long totalUsers;

    private long totalCategories;

    private long totalProducts;

    private long totalOrders;

    private long pendingOrders;

    private long completedOrders;

    private long cancelledOrders;

    private BigDecimal totalRevenue;
}
