package com.example.ItemService.repository;

import com.example.ItemService.entity.Item;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemsRepository extends MongoRepository<Item, String> {
    Optional<Item> findByUpc(String upc);

}
