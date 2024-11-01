package com.example.ItemService.service;

import com.example.ItemService.dto.ItemDto;

import java.util.List;
import java.util.Optional;

public interface ItemService {
    ItemDto createItem(ItemDto itemDto);

    List<ItemDto> getAllItems();

    Optional<ItemDto> getItemById(String id);

    void deleteItem(String id);

    ItemDto updateInventory(String id, int units);

    List<ItemDto> getItemsByCategory(String category);

    boolean checkInventory(String itemId, int requestAmount);
}
