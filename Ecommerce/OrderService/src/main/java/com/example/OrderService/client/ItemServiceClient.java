package com.example.OrderService.client;

import com.example.OrderService.model.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class ItemServiceClient {

    private final RestTemplate restTemplate;

    public Item getItemById(String itemId) {
        return restTemplate.getForObject("http://localhost:8082/api/items/" + itemId, Item.class);
    }

    public void deductItemUnits(String itemId, int unitsToDeduct) {
        restTemplate.put("http://localhost:8082/api/items/" + itemId + "/deduct?units=" + unitsToDeduct, null);
    }

}
