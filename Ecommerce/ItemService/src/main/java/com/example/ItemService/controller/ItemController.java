package com.example.ItemService.controller;

import com.example.ItemService.dto.ItemDto;
import com.example.ItemService.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
@Tag(name = "Item Controller", description = "APIs for Item Management")
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    @Operation(summary = "Add a new item")
    public ResponseEntity<?> addItem(@RequestBody ItemDto itemDto) {
        try {
            ItemDto createdItem = itemService.createItem(itemDto);
            return new ResponseEntity<>(createdItem, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @GetMapping
    @Operation(summary = "Get all items")
    public ResponseEntity<List<ItemDto>> getAllItems() {
        return new ResponseEntity<>(itemService.getAllItems(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get item by ID")
    public ResponseEntity<ItemDto> getItemById(@PathVariable String id) {
        return itemService.getItemById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/category/{category}")
    @Operation(summary = "Get items by category")
    public ResponseEntity<List<ItemDto>> getItemsByCategory(@PathVariable String category) {
        return new ResponseEntity<>(itemService.getItemsByCategory(category), HttpStatus.OK);
    }

    @GetMapping("/{id}/inventory")
    @Operation(summary = "Check inventory")
    public ResponseEntity<Boolean> checkInventory(@PathVariable String id, @RequestParam int requestAmount) {
        return new ResponseEntity<>(itemService.checkInventory(id, requestAmount), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update item inventory")
    public ResponseEntity<ItemDto> updateItem(@PathVariable String id, @RequestParam int addAmount) {
        return new ResponseEntity<>(itemService.updateInventory(id, addAmount), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete item")
    public ResponseEntity<String> deleteItem(@PathVariable String id) {
        itemService.deleteItem(id);
        return new ResponseEntity<>("Item deleted", HttpStatus.OK);
    }
}
