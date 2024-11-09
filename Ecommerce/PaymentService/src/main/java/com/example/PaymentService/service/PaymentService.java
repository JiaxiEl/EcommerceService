package com.example.PaymentService.service;

import com.example.PaymentService.dto.PaymentDTO;
import com.example.PaymentService.entity.Payment;

import java.util.Optional;

public interface PaymentService {
    Payment createPayment(Payment payment);
    Optional<Payment> updatePayment(String transactionId, Payment paymentDetails);
    boolean reversePayment(String transactionId);
    Optional<Payment> lookupPayment(String transactionId);
}
