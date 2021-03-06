package ru.letmerent.core.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.letmerent.core.converters.OrderConverter;
import ru.letmerent.core.dto.OrderDto;
import ru.letmerent.core.entity.Order;
import ru.letmerent.core.exceptions.models.ApplicationError;
import ru.letmerent.core.security.IAuthenticationFacade;
import ru.letmerent.core.services.EmailService;
import ru.letmerent.core.services.OrderService;
import ru.letmerent.core.services.impl.CartService;

import java.security.Principal;
import java.util.Collection;

import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api/v1/orders")
@Tag(name = "API для работы с заказом")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;

    private final OrderConverter orderConverter;

    private final CartService cartService;

    private final IAuthenticationFacade authenticationFacade;
    
    private final EmailService emailService;
    
    private final ObjectMapper mapper;
    
    private final ApplicationError applicationError;

    @Operation(summary = "Создание заказа")
    @PostMapping("/{uuid}")
    @ApiResponse(
            responseCode = "201",
            description = "Заказ успешно создан.",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = OrderDto.class)
            ))
    ResponseEntity<OrderDto> addNewOrder(Principal principal, @PathVariable String uuid) {
        String cartUuid = cartService.getCurrentCartUuid(principal, uuid);
        OrderDto orderDto = orderConverter.convertCartToOrder(principal, cartUuid);
        Order order = orderService.createOrder(orderConverter.convertToOrder(orderDto));
        emailService.sendNotification(order);
        return new ResponseEntity<>(orderConverter.convertToOrderDto(order), HttpStatus.CREATED);
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
        Order order = orderService.findOrderById(id).orElseThrow(() -> new RuntimeException("Заказ с id " + id + " не найден!"));
        return new ResponseEntity<>(orderConverter.convertToOrderDto(order), HttpStatus.OK);
    }

    @Operation(summary = "Вывод информации по всем заказам пользователя")
    @GetMapping
    @ApiResponse(
            responseCode = "200",
            description = "Список заказов.",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(
                            schema = @Schema(
                                    implementation = OrderDto.class))
            ))
    ResponseEntity<Object> getOrdersByUserId() {
        String login = authenticationFacade.getLogin();
        if(isNull(login)){
            return ResponseEntity.badRequest()
                .body(mapper.valueToTree(applicationError.generateError(HttpStatus.BAD_REQUEST.value(), "notAuth")));
        }
        Collection<OrderDto> result = orderService.findOrdersByUserName(login).stream().map(orderConverter::convertToOrderDto).collect(toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
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
