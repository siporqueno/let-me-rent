package ru.letmerent.core.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.letmerent.core.entity.Order;
import ru.letmerent.core.repositories.OrderRepository;
import ru.letmerent.core.services.OrderService;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;

    @Override
    public Order createOrder(Order order) {
        return repository.save(order);
    }
}
