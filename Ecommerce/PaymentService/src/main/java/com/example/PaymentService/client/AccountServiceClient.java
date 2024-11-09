package com.example.PaymentService.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

@Service
@Slf4j
public class AccountServiceClient {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${account.service.base-url}")
    private String accountServiceBaseUrl;

    @Value("${jwt.secret}")
    private String jwtSecret;

    private String getAuthToken() {
        if (jwtSecret == null || jwtSecret.isEmpty()) {
            throw new IllegalStateException("JWT token is not set in the application properties.getAuthToken");
        }
        String token = jwtSecret;
        log.debug("Generated auth token for request: {}", token);
        return token;
    }

    public Double getBalance(Long id) {
        try {
            String url = accountServiceBaseUrl + "/api/accounts/" + id + "/balance";
            HttpHeaders headers = new HttpHeaders();
            //headers.set("Authorization", "Bearer " + getAuthToken());
            HttpEntity<String> entity = new HttpEntity<>(headers);
            log.debug("Sending request to AccountService to get balance for ID: {} with token: {}", id, headers.get("Authorization"));
            ResponseEntity<Double> response = restTemplate.exchange(url, HttpMethod.GET, entity, Double.class);
            return response.getBody();
        } catch (HttpClientErrorException e) {
            log.error("Failed to retrieve balance for account ID: {} - Status: {}", id, e.getStatusCode(), e);
            throw new RuntimeException("Error retrieving balance for account ID: " + id, e);
        } catch (RestClientException e) {
            log.error("Failed to retrieve balance for account ID: {}", id, e);
            throw new RuntimeException("Error retrieving balance for account ID: " + id, e);
        }
    }

    public void updateAccountBalance(Long id, Double amount) {
        try {
            String url = accountServiceBaseUrl + "/api/accounts/" + id + "/update-balance?amount=" + amount;
            HttpHeaders headers = new HttpHeaders();
            //headers.set("Authorization", "Bearer " + getAuthToken());
            HttpEntity<String> entity = new HttpEntity<>(headers);
            log.debug("Sending request to AccountService to update balance for ID: {} with token: {}", id, headers.get("Authorization"));
            restTemplate.exchange(url, HttpMethod.PUT, entity, Void.class);
        } catch (RestClientException e) {
            log.error("Failed to update balance for account ID: {}", id, e);
            throw new RuntimeException("Error updating balance for account ID: " + id, e);
        }
    }
}
