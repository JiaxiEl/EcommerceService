package com.example.ItemService.exception;

public class ItemNotFoundException extends RuntimeException {
    public ItemNotFoundException(String id) {
        super("Item not found with ID: " + id);
    }
}
