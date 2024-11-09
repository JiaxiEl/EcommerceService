package com.example.OrderService.service.impl;

import com.example.OrderService.client.ItemServiceClient;
import com.example.OrderService.dto.OrderDTO;
import com.example.OrderService.entity.Order;
import com.example.OrderService.exception.InsufficientStockException;
import com.example.OrderService.model.Item;
import com.example.OrderService.repository.OrderRepository;
import com.example.OrderService.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.UUID;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ItemServiceClient itemServiceClient;

    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {
        Map<Item, Integer> itemQuantities  = new HashMap<>();
        BigDecimal totalAmount = new BigDecimal(0.0);
        for (String Itemid : orderDTO.getItemIds()) {
            Item item = itemServiceClient.getItemById(Itemid);
            itemQuantities.put(item, itemQuantities.getOrDefault(item, 0) + 1);
            totalAmount = totalAmount.add(new BigDecimal(item.getPrice()));
        }
        for (Map.Entry<Item, Integer> entry : itemQuantities.entrySet()) {
            Item item = entry.getKey();
            int requiredQuantity = entry.getValue();
            if (item.getAvailableUnits() < requiredQuantity) {
                throw new InsufficientStockException("Not enough stock for item: " + item.getName());
            }
        }

        Order order = Order.builder()
                .orderId(UUID.randomUUID())
                .userId(orderDTO.getUserId())
                .itemIds(orderDTO.getItemIds())
                .totalAmount(totalAmount)
                .status("CREATED")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        orderRepository.save(order);

        // Send Kafka message
        kafkaTemplate.send("order-topic", "Order Created: " + order.getOrderId());

        // Update item stock in ItemService
        for (Map.Entry<Item, Integer> entry : itemQuantities.entrySet()) {
            Item item = entry.getKey();
            int quantityToDeduct  = entry.getValue();
            itemServiceClient.deductItemUnits(item.getItemId(), quantityToDeduct);
        }

        return convertToDTO(order);
    }


    @Override
    public OrderDTO getOrderById(UUID orderId) {
        return orderRepository.findById(orderId).map(this::convertToDTO).orElse(null);
    }

    @Override
    public OrderDTO updateOrderStatus(UUID orderId, String status) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order != null) {
            order.setStatus(status);
            order.setUpdatedAt(LocalDateTime.now());
            orderRepository.save(order);
            kafkaTemplate.send("order-topic", "Order Status Updated: " + orderId);
        }
        return convertToDTO(order);
    }

    private OrderDTO convertToDTO(Order order) {
        return OrderDTO.builder()
                .orderId(order.getOrderId())
                .userId(order.getUserId())
                .itemIds(order.getItemIds())
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus())
                .build();
    }
}
