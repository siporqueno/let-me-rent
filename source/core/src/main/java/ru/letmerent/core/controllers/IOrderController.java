package ru.letmerent.core.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.letmerent.core.entity.ICart;
import ru.letmerent.core.entity.IOrder;

import java.util.Collection;

@Controller
@RequestMapping("/api/v1/orders")
public interface IOrderController {

    IOrder addNewOrder(ICart ICart);

    IOrder getOrderById(Long id);

    Collection<IOrder> getOrdersByUserId(Long id);

    IOrder modifyOrder(IOrder IOrder);
}
