package ru.letmerent.core.services;

import ru.letmerent.core.entity.Order;

public interface EmailService {
    void sendNotification(Order order);
}
