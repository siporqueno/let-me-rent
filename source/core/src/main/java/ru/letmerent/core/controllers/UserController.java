package ru.letmerent.core.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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

import java.util.Date;

@RestController
@RequiredArgsConstructor
@Tag(name = "API для работы с пользователями")
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final UserConverter userConverter;
    private final ObjectMapper mapper;

    @Operation(summary = "Регистрация нового пользователя")
    @ApiResponse(responseCode = "201",
            description = "Аутентификая прошла успешно.")
    @ApiResponse(responseCode = "404", description = "Не корректные параметры запроса",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApplicationError.class)))
    @PostMapping
    public ResponseEntity<?> registerNewUser(@RequestBody UserDto userDto) {
        if (!userDto.getPassword().equals(userDto.getPasswordConfirmation())) {
            return ResponseEntity.unprocessableEntity()
                    .body(mapper.valueToTree(ApplicationError.builder()
                            .errorCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
                            .userMessage("Incorrect password confirmation")
                            .date(new Date())
                            .build()));
        }
        if (userService.existByEmail(userDto.getEmail())) {
            return ResponseEntity.badRequest()
                    .body(mapper.valueToTree(ApplicationError.builder()
                            .errorCode(HttpStatus.BAD_REQUEST.value())
                            .userMessage("Email is already in use!")
                            .date(new Date())
                            .build()));
        }
        if (userService.existByUsername(userDto.getUserName())) {
            return ResponseEntity.badRequest()
                    .body(mapper.valueToTree(ApplicationError.builder()
                            .errorCode(HttpStatus.BAD_REQUEST.value())
                            .userMessage("Username is already taken!")
                            .date(new Date())
                            .build()));
        }
        userService.saveUser(userDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Получение информации о пользователе")
    @ApiResponse(responseCode = "200", description = "Информация о пользователе",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserDto.class)))
    @GetMapping("/{username}")
    public UserDto getUser(@PathVariable String username) {
        return userConverter.userToUserDtoConverter(userService.findByUsername(username));
    }

    @Operation(summary = "Модификация пользовательских данных")
    @ApiResponse(responseCode = "200", description = "Информация о пользователе успешно изменена")
    @ApiResponse(responseCode = "422", description = "Введены не корректные данные",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApplicationError.class)))
    @PutMapping
    public ResponseEntity<?> modifyUser(@RequestBody UserDto userDto) {
        if (!userDto.getPassword().equals(userDto.getPasswordConfirmation())) {
            return ResponseEntity.unprocessableEntity()
                    .body(mapper.valueToTree(ApplicationError.builder()
                            .errorCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
                            .userMessage("Incorrect password confirmation")
                            .date(new Date())
                            .build()));
        }
        userService.saveUser(userDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Модификация пользовательских данных")
    @ApiResponse(responseCode = "200", description = "пользователь успешно удален")
    @ApiResponse(responseCode = "422", description = "Введены не корректные данные",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApplicationError.class)))
    @DeleteMapping
    public ResponseEntity<?> deleteUser(@RequestBody UserDto userDto) {
        if (!userDto.getPassword().equals(userDto.getPasswordConfirmation())) {
            return ResponseEntity.unprocessableEntity()
                    .body(mapper.valueToTree(ApplicationError.builder()
                            .errorCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
                            .userMessage("Incorrect password confirmation")
                            .date(new Date())
                            .build()));
        }
        userService.deleteUser(userDto.getUserName());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
