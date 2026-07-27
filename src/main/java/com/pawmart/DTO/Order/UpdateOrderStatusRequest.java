package com.pawmart.DTO.Order;

import com.pawmart.enums.OrderStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateOrderStatusRequest {
    @NotBlank(message = "Status is required")
    private OrderStatus status;
}
