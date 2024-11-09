package com.example.OrderService.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Item {
    private String itemId;
    private String name;
    private String description;
    private String price;
    private int availableUnits;
    private String category;
}
