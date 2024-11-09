package com.example.ItemService.service.impl;

import com.example.ItemService.dto.ItemDto;
import com.example.ItemService.entity.Item;
import com.example.ItemService.exception.ItemNotFoundException;
import com.example.ItemService.repository.ItemsRepository;
import com.example.ItemService.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Validated
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemsRepository itemsRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public ItemDto createItem(ItemDto itemDto) {
        try {
            Item item = ItemDto.toEntity(itemDto);
            Item savedItem = itemsRepository.save(item);
            return ItemDto.fromEntity(savedItem);
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "UPC must be unique", ex);
        }
    }


    @Override
    public List<ItemDto> getAllItems() {
        return itemsRepository.findAll().stream().map(ItemDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Optional<ItemDto> getItemById(String id) {
        return itemsRepository.findById(id).map(ItemDto::fromEntity);
    }

    @Override
    public ItemDto deductItemUnits(String id, int units) {
        Item item = itemsRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(id));
        if (item.getAvailableUnits() < units) {
            throw new IllegalArgumentException("Insufficient units available");
        }
        item.setAvailableUnits(item.getAvailableUnits() - units);
        itemsRepository.save(item);
        kafkaTemplate.send("item-topic", "Item units deducted for item ID: " + id);
        return ItemDto.fromEntity(item);
    }
    @Override
    public Optional<ItemDto> getItemByUpc(String upc) {
        return itemsRepository.findByUpc(upc).map(ItemDto::fromEntity);
    }

}
