package ru.letmerent.core.services;

import ru.letmerent.core.entity.OrderItem;

import java.util.List;

public interface OrderItemService {
    List<OrderItem> findAllByInstrumentId(Long instrumentId);
}
