package ru.letmerent.core.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.letmerent.core.dto.OrderDto;

import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("/api/v1/orders")
@Tag(name = "API для работы с заказом")
public class OrderController {

    @Operation(summary = "Создание заказа")
    @PostMapping
    @ApiResponse(
            responseCode = "201",
            description = "Заказ успешно создан.",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = OrderDto.class)
            ))
    ResponseEntity<OrderDto> addNewOrder(OrderDto orderDto) {
        return new ResponseEntity<>(new OrderDto(), HttpStatus.CREATED);
    }

    @Operation(summary = "Информация по заказу")
    @GetMapping("/{id}")
    @ApiResponse(
            responseCode = "200",
            description = "Информация по заказу.",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            implementation = OrderDto.class))
    )
    ResponseEntity<OrderDto> getOrderById(@Parameter(description = "Идентификатор заказа") @PathVariable Long id) {
        return new ResponseEntity<>(new OrderDto(), HttpStatus.OK);
    }

    @Operation(summary = "Вывод информации по всем заказам пользователя")
    @GetMapping("/{userId}") //Как вариант, для инфо о своих заказах, может не передавать ничего, а искать заказы по имени пользователя, которое мы возьмем просто из Principal? (можно хоть отдельный метод сделать для такого варианта поиска)
    @ApiResponse(
            responseCode = "200",
            description = "Список заказов.",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(
                            schema = @Schema(
                                    implementation = OrderDto.class))
            ))
    ResponseEntity<Collection<OrderDto>> getOrdersByUserId(@PathVariable Long userId) {
        return new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK);//TODO: здесь пока заглушка, надо доработать, чтобы возвращались заказы
    }

    @Operation(summary = "Изменение информации по заказу")
    @PutMapping
    @ApiResponse(
            responseCode = "200",
            description = "Заказ успешно изменён.",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            implementation = OrderDto.class))
    )
    ResponseEntity<OrderDto> modifyOrder(@RequestBody OrderDto orderDto) {
        return new ResponseEntity<>(new OrderDto(),HttpStatus.OK);
    }
}
