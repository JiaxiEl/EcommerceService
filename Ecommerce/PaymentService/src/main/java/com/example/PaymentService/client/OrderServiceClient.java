package com.example.PaymentService.client;

import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@Slf4j
public class OrderServiceClient {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${order.service.base-url}")
    private String orderServiceBaseUrl;

    @Value("${jwt.secret}")
    private String jwtSecret;

    private String getAuthToken() {
        if (jwtSecret == null || jwtSecret.isEmpty()) {
            throw new IllegalStateException("JWT token is not set in the application properties. Account");
        }
        return jwtSecret;
    }

    public BigDecimal getOrderTotalAmount(UUID orderId) {
        try {
            String url = orderServiceBaseUrl + "/api/orders/" + orderId + "/total-amount";
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + getAuthToken());
            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<BigDecimal> response = restTemplate.exchange(url, HttpMethod.GET, entity, BigDecimal.class);
            return response.getBody();
        } catch (RestClientException e) {
            log.error("Failed to retrieve total amount for order ID: {}", orderId, e);
            throw new RuntimeException("Error retrieving total amount for order ID: " + orderId, e);
        }
    }

    public void updateOrderStatus(UUID orderId, String status) {
        try {
            String url = orderServiceBaseUrl + "/api/orders/" + orderId + "/status?status=" + status;
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + getAuthToken());
            HttpEntity<String> entity = new HttpEntity<>(headers);
            restTemplate.exchange(url, HttpMethod.PUT, entity, Void.class);
        } catch (RestClientException e) {
            log.error("Failed to update status for order ID: {}", orderId, e);
            throw new RuntimeException("Error updating status for order ID: " + orderId, e);
        }
    }
}
