package ru.letmerent.core.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.letmerent.core.entity.Order;
import ru.letmerent.core.repositories.OrderRepository;
import ru.letmerent.core.services.OrderService;

import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;

    @Override
    public Order createOrder(Order order) {
        return repository.save(order);
    }

    @Override
    public Optional<Order> findOrderById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Collection<Order> findOrdersByUserName(String userName) {
        return repository.findAllByUserName(userName);
    }
}
