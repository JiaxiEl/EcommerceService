package com.example.PaymentService.controller;

import com.example.PaymentService.entity.Payment;
import com.example.PaymentService.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/submit")
    public ResponseEntity<String> submitPayment(@RequestBody Payment payment) {
        System.out.println("Received Payment Request: " + payment);
        try {
            Payment createdPayment = paymentService.createPayment(payment);
            return ResponseEntity.ok("Payment completed successfully. Transaction ID: " + createdPayment.getTransactionId());
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }



    @PutMapping("/update/{transactionId}")
    public ResponseEntity<Payment> updatePayment(@PathVariable String transactionId, @RequestBody Payment payment) {
        Optional<Payment> updatedPayment = paymentService.updatePayment(transactionId, payment);
        return updatedPayment.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/reverse/{transactionId}")
    public ResponseEntity<String> reversePayment(@PathVariable String transactionId) {
        boolean success = paymentService.reversePayment(transactionId);
        if (success) {
            return ResponseEntity.ok("Payment reversed successfully.");
        } else {
            return ResponseEntity.status(400).body("Failed to reverse payment.");
        }
    }

    @GetMapping("/lookup/{transactionId}")
    public ResponseEntity<Payment> lookupPayment(@PathVariable String transactionId) {
        Optional<Payment> payment = paymentService.lookupPayment(transactionId);
        return payment.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}