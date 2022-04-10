package ru.letmerent.core.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.letmerent.core.entity.Cart;
import ru.letmerent.core.entity.Order;

import java.util.Collection;

@Controller
@RequestMapping("/api/v1/orders")
public interface OrderController {

    Order addNewOrder(Cart cart);

    Order getOrderById(Long id);

    Collection<Order> getOrdersByUserId(Long id);

    Order modifyOrder(Order order);
}
