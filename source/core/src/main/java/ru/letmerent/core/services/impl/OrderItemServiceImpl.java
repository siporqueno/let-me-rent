package ru.letmerent.core.services.impl;

import org.springframework.stereotype.Service;
import ru.letmerent.core.entity.Instrument;
import ru.letmerent.core.entity.OrderItem;
import ru.letmerent.core.repositories.OrderItemRepository;
import ru.letmerent.core.services.OrderItemService;

import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService {
    
    private final OrderItemRepository orderItemRepository;
    
    public OrderItemServiceImpl(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }
    
    @Override
    public List<OrderItem> findAllByInstrument(Instrument instrument) {
        return orderItemRepository.findAllByInstrument(instrument);
    }
}
