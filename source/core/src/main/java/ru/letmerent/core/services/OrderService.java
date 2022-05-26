package ru.letmerent.core.services;

import ru.letmerent.core.entity.Order;

import java.util.Collection;
import java.util.Optional;

public interface OrderService {
    Order createOrder(Order order);
    Optional<Order> findOrderById(Long id);
    Collection<Order> findOrdersByUserName(String userName);
}
