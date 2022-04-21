package ru.letmerent.core.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.letmerent.core.converters.UserConverter;
import ru.letmerent.core.dto.UserDto;
import ru.letmerent.core.exceptions.ApplicationError;
import ru.letmerent.core.services.impl.UserService;

@RestController
@RequiredArgsConstructor
@Tag(name = "API для работы с пользователями")
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final UserConverter userConverter;

    @Operation(summary = "Регистрация нового пользователя")
    @ApiResponse(
            responseCode = "200",
            description = "Аутентификая прошла успешно.")
    @PostMapping
    public ResponseEntity<?> createNewUser(@RequestBody UserDto userDto) {
        if (!userDto.getPassword().equals(userDto.getPasswordConfirmation())) {
            return new ResponseEntity<>(new ApplicationError(this.getClass().toString(),
                    HttpStatus.UNPROCESSABLE_ENTITY.toString(),
                    "Incorrect password confirmation"),
                    HttpStatus.UNPROCESSABLE_ENTITY);
        }
        userService.saveUser(userDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{username}")
    public UserDto getUser(@PathVariable String username) {
        return userConverter.userToUserDtoConverter(userService.findByUsername(username));
    }

    @PutMapping
    public ResponseEntity<?> modifyUser(@RequestBody UserDto userDto) {
        if (!userDto.getPassword().equals(userDto.getPasswordConfirmation())) {
            return new ResponseEntity<>(new ApplicationError(this.getClass().toString(),
                    HttpStatus.UNPROCESSABLE_ENTITY.toString(),
                    "Incorrect password confirmation"),
                    HttpStatus.UNPROCESSABLE_ENTITY);
        }
        userService.saveUser(userDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(@RequestBody UserDto userDto) {
        if (!userDto.getPassword().equals(userDto.getPasswordConfirmation())) {
            return new ResponseEntity<>(new ApplicationError(this.getClass().toString(),
                    HttpStatus.UNPROCESSABLE_ENTITY.toString(),
                    "Incorrect password confirmation"),
                    HttpStatus.UNPROCESSABLE_ENTITY);
        }
        userService.deleteUser(userDto.getUserName());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
