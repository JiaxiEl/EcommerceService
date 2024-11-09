package com.example.ItemService.service;

import com.example.ItemService.dto.ItemDto;
import com.example.ItemService.entity.Item;

import java.util.List;
import java.util.Optional;

public interface ItemService {
    ItemDto createItem(ItemDto itemDto);
    List<ItemDto> getAllItems();
    Optional<ItemDto> getItemById(String id);
    ItemDto deductItemUnits(String id, int units);
    Optional<ItemDto> getItemByUpc(String upc);
}
