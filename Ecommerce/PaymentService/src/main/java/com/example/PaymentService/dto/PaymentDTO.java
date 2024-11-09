package com.example.PaymentService.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDTO {
    private Long id;
    private Long userId;
    private String orderId;
    private Double amount;
    private String status;
    private String transactionId;
    private LocalDateTime createTime;
    private LocalDateTime updateDateTime;
}
