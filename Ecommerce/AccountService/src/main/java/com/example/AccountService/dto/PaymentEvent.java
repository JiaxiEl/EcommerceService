package com.example.AccountService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentEvent {
    private String transactionId;
    private String email;
    private double amount;
    private String status;
}
