package com.example.OrderService.service;

import com.example.OrderService.dto.OrderDTO;
import java.util.UUID;

public interface OrderService {
    OrderDTO createOrder(OrderDTO orderDTO);
    OrderDTO getOrderById(UUID orderId);
    OrderDTO updateOrderStatus(UUID orderId, String status);
}