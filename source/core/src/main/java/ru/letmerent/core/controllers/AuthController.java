package ru.letmerent.core.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.letmerent.core.dto.UserDto;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "API для работы с сервисом аутентификации")
public class AuthController {

    @Operation(summary = "Аутентификация")
    @PostMapping
    @ApiResponse(
            responseCode = "200",
            description = "Аутентификая прошла успешно.",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserDto.class)
            ))
    ResponseEntity<UserDto> authenticate(@RequestHeader String token) {
        return new ResponseEntity<>(new UserDto(), HttpStatus.OK);
    }

    @Operation(summary = "Регистрация")
    @PostMapping("/reg")
    @ApiResponse(
            responseCode = "201",
            description = "Пользователь успешно зарегистрирован.")
    ResponseEntity<?> registerNewUser(@RequestBody UserDto user) {
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Изменить реквизиты пользователя")
    @PutMapping
    @ApiResponse(
            responseCode = "200",
            description = "Пользователь успешно изменён."
            )
    ResponseEntity<Boolean> changeUserCredentials(@RequestBody UserDto user) {
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @Operation(summary = "Удаление пользователя")
    @DeleteMapping
    @ApiResponse(
            responseCode = "200",
            description = "Пользователь успешно удалён."
    )
    ResponseEntity<Boolean> deleteUser(@RequestBody UserDto user) {
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
