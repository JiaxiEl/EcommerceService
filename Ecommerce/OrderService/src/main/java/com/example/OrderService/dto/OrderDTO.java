package com.example.OrderService.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private UUID orderId;
    private String userId;
    private List<String> itemIds;
    private BigDecimal totalAmount;
    private String status;
}
