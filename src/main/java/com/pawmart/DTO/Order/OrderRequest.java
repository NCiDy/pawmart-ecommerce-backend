package com.pawmart.DTO.Order;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequest {
    @NotNull(message = "Shipping address is required")
    private Long addressId;

    @NotBlank(message = "Payment method is required")
    private String paymentMethod;

    @Size(max = 500, message = "Note must not exceed 500 characters")
    private String note;
}
