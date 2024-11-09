package com.example.PaymentService.service.impl;

import com.example.PaymentService.client.AccountServiceClient;
import com.example.PaymentService.client.OrderServiceClient;
import com.example.PaymentService.entity.Payment;
import com.example.PaymentService.exception.InsufficientBalanceException;
import com.example.PaymentService.exception.OrderNotFoundException;
import com.example.PaymentService.repository.PaymentRepository;
import com.example.PaymentService.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private static final String PAYMENT_TOPIC = "payment-topic";
    private final PaymentRepository paymentRepository;
    private final AccountServiceClient accountServiceClient;
    private final OrderServiceClient orderServiceClient;

    @Override
    public Payment createPayment(Payment payment) {
        UUID orderId = UUID.fromString(payment.getOrderId());

        // Get order total amount
        BigDecimal totalAmount = orderServiceClient.getOrderTotalAmount(orderId);
        if (totalAmount == null) {
            throw new OrderNotFoundException("Order not found for orderId: " + orderId);
        }

        log.info("Calculated totalAmount: {}", totalAmount);

        Long userId = payment.getUserId();
        Double accountBalance = accountServiceClient.getBalance(userId);
        if (accountBalance == null || BigDecimal.valueOf(accountBalance).compareTo(totalAmount) < 0) {
            log.info("Calculated totalAmount: {}", userId);
            throw new InsufficientBalanceException("Insufficient balance for userId: " + userId);
        }

        accountServiceClient.updateAccountBalance(userId, -totalAmount.doubleValue());


        orderServiceClient.updateOrderStatus(orderId, "COMPLETED");


        payment.setTransactionId(UUID.randomUUID().toString());
        payment.setStatus("COMPLETED");
        payment.setAmount(totalAmount.doubleValue());

        Payment savedPayment = paymentRepository.save(payment);
        log.info("Payment created and Kafka message sent for transactionId: {}", payment.getTransactionId());

        return savedPayment;
    }

    @Override
    public Optional<Payment> updatePayment(String transactionId, Payment paymentDetails) {
        return paymentRepository.findByTransactionId(transactionId).map(existingPayment -> {
            existingPayment.setStatus(paymentDetails.getStatus());
            existingPayment.setAmount(paymentDetails.getAmount());
            Payment updatedPayment = paymentRepository.save(existingPayment);
            log.info("Payment updated and Kafka message sent for transactionId: {}", transactionId);
            return updatedPayment;
        });
    }

    @Override
    public boolean reversePayment(String transactionId) {
        return paymentRepository.findByTransactionId(transactionId).map(payment -> {
            if (!"REFUNDED".equals(payment.getStatus())) {
                payment.setStatus("REFUNDED");
                paymentRepository.save(payment);
                log.info("Payment reversed and Kafka message sent for transactionId: {}", transactionId);
                return true;
            }
            return false;
        }).orElse(false);
    }

    @Override
    public Optional<Payment> lookupPayment(String transactionId) {
        return paymentRepository.findByTransactionId(transactionId);
    }
}
