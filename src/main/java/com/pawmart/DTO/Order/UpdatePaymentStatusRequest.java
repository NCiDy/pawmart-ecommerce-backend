package com.pawmart.DTO.Order;

import com.pawmart.enums.PaymentStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePaymentStatusRequest {
    @NotBlank(message = "Payment Status is required")
    private PaymentStatus paymentStatus;
}
