package com.example.ItemService.service.impl;

import com.example.ItemService.repository.ItemsRepository;
import com.example.ItemService.entity.Item;
import com.example.ItemService.exception.ItemNotFoundException;
import com.example.ItemService.dto.ItemDto;
import com.example.ItemService.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemsRepository itemsRepository;

    @Override
    public ItemDto createItem(ItemDto itemDto) {
        Item newItem = toItemEntity(itemDto);
        Item savedItem = itemsRepository.save(newItem);
        return toItemDto(savedItem);
    }

    @Override
    public List<ItemDto> getAllItems() {
        return itemsRepository.findAll().stream().map(this::toItemDto).collect(Collectors.toList());
    }

    @Override
    public Optional<ItemDto> getItemById(String id) {
        return itemsRepository.findById(id).map(this::toItemDto);
    }

    @Override
    public void deleteItem(String id) {
        if (!itemsRepository.existsById(id)) {
            throw new ItemNotFoundException(id);
        }
        itemsRepository.deleteById(id);
    }

    @Override
    public ItemDto updateInventory(String id, int units) {
        Item item = itemsRepository.findItemByItemId(id);
        if (item == null) throw new ItemNotFoundException(id);
        item.setAvailableUnits(item.getAvailableUnits() + units);
        itemsRepository.save(item);
        return toItemDto(item);
    }

    @Override
    public List<ItemDto> getItemsByCategory(String category) {
        return itemsRepository.findItemsByCategory(category).stream().map(this::toItemDto).collect(Collectors.toList());
    }

    @Override
    public boolean checkInventory(String itemId, int requestAmount) {
        Item item = itemsRepository.findItemByItemId(itemId);
        return item != null && item.getAvailableUnits() >= requestAmount;
    }

    private ItemDto toItemDto(Item item) {
        return new ItemDto(item.getItemId(), item.getName(), item.getDescription(), item.getPrice(),
                item.getUpc(), item.getImageUrl(), item.getAvailableUnits(), item.getCategory());
    }

    private Item toItemEntity(ItemDto itemDto) {
        Item item = new Item();
        item.setName(itemDto.getName());
        item.setDescription(itemDto.getDescription());
        item.setPrice(itemDto.getPrice());
        item.setUpc(itemDto.getUpc());
        item.setImageUrl(itemDto.getImageUrl());
        item.setAvailableUnits(itemDto.getAvailableUnits());
        item.setCategory(itemDto.getCategory());
        return item;
    }
}
