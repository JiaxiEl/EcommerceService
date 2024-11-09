package com.example.OrderService.controller;

import com.example.OrderService.dto.OrderDTO;
import com.example.OrderService.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "Order Controller", description = "APIs for Order Management")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @Operation(summary = "Create a new order")
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO) {
        OrderDTO response = orderService.createOrder(orderDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{orderId}")
    @Operation(summary = "Get order by ID")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable UUID orderId) {
        OrderDTO response = orderService.getOrderById(orderId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/{orderId}/total-amount")
    @Operation(summary = "Get total amount for an order by ID")
    public ResponseEntity<BigDecimal> getTotalAmountByOrderId(@PathVariable UUID orderId) {
        OrderDTO response = orderService.getOrderById(orderId);
        if (response != null && response.getTotalAmount() != null) {
            return new ResponseEntity<>(response.getTotalAmount(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{orderId}/status")
    @Operation(summary = "Update order status")
    public ResponseEntity<OrderDTO> updateOrderStatus(@PathVariable UUID orderId, @RequestParam String status) {
        return new ResponseEntity<>(orderService.updateOrderStatus(orderId, status), HttpStatus.OK);
    }
}