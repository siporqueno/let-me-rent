package ru.letmerent.core.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.letmerent.core.entity.ICart;
import ru.letmerent.core.entity.User;

@Controller
@RequestMapping("/api/v1/carts")
public interface ICartController {

    ICart createUserCart(User user);

    ICart getCartByUserId(Long id);

    ICart addInstrumentInCart(Long id);

    ICart removeInstrumentFromCart(Long id);

    void clearCart();
}
