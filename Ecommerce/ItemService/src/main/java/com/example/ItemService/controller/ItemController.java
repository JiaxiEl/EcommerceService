package com.example.ItemService.controller;

import com.example.ItemService.dto.ItemDto;
import com.example.ItemService.service.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    public ResponseEntity<ItemDto> addItem(@Valid @RequestBody ItemDto itemDto) {
        return new ResponseEntity<>(itemService.createItem(itemDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ItemDto>> getAllItems() {
        return new ResponseEntity<>(itemService.getAllItems(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemDto> getItemById(@PathVariable String id) {
        return itemService.getItemById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping("/{id}/deduct")
    public ResponseEntity<ItemDto> deductItemUnits(@PathVariable String id, @RequestParam int units) {
        return new ResponseEntity<>(itemService.deductItemUnits(id, units), HttpStatus.OK);
    }
    @GetMapping("/upc/{upc}")
    public ResponseEntity<ItemDto> getItemByUpc(@PathVariable String upc) {
        return itemService.getItemByUpc(upc)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

}
