package ru.letmerent.core.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.letmerent.core.entity.User;

@Controller
@RequestMapping("/api/v1/auth")
public interface IAuthController {

    User authenticate(String token);

    void registerNewUser(User user);

    boolean changeUserCredentials(User user);

    boolean deleteUser(User user);
}
