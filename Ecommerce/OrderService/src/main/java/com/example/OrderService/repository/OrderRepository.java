package com.example.OrderService.repository;

import com.example.OrderService.entity.Order;
import org.springframework.data.cassandra.repository.CassandraRepository;
import java.util.UUID;

public interface OrderRepository extends CassandraRepository<Order, UUID> {
}