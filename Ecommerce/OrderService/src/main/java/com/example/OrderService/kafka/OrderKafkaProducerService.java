package com.example.OrderService.kafka;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderKafkaProducerService {

    private static final Logger logger = LoggerFactory.getLogger(OrderKafkaProducerService.class);
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String message) {
        logger.info("Sending message to Kafka: {}", message);
        kafkaTemplate.send("order-topic", message);
    }
}
