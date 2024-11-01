package com.example.ItemService.repository;

import com.example.ItemService.entity.Item;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemsRepository extends MongoRepository<Item, String> {
    List<Item> findItemsByCategory(String category);
    Item findItemByItemId(String id);
    long deleteItemByItemId(String id);
}
