package ru.letmerent.core.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.letmerent.core.entity.Cart;
import ru.letmerent.core.entity.User;

@Controller
@RequestMapping("/api/v1/carts")
public interface CartController {

    Cart createUserCart(User user);

    Cart getCartByUserId(Long id);

    Cart addInstrumentInCart(Long id);

    Cart removeInstrumentFromCart(Long id);

    void clearCart();
}
