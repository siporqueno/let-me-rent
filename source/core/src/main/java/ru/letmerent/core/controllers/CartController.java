package ru.letmerent.core.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.letmerent.core.dto.Cart;
import ru.letmerent.core.dto.OrderDto;
import ru.letmerent.core.dto.StringResponse;
import ru.letmerent.core.entity.User;
import ru.letmerent.core.services.impl.CartService;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor
@Tag(name = "API для работы с сервисом по действиям с корзиной")
public class CartController {

    private final CartService cartService;

    @Operation(summary = "Получение корзины")
    @GetMapping("/{uuid}")
    @ApiResponse(
            responseCode = "200",
            description = "Корзина успешно получена",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Cart.class)
            ))
    public Cart getCart(Principal principal, @PathVariable String uuid) {
        return cartService.getCurrentCart(getCurrentCartUuid(principal, uuid));
    }

    @Operation(summary = "Получение uuid корзины")
    @GetMapping("/generate")
    @ApiResponse(
            responseCode = "200",
            description = "Отправлен uuid для корзины",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = StringResponse.class)
            ))
    public StringResponse getCartUuid() {
        return new StringResponse(cartService.generateCartUuid());
    }

    @Operation(summary = "Добавление в корзину")
    @GetMapping("/{uuid}/add/{instrumentId}/{startDate}/{endDate}")
    //TODO: с фронта из фильтров даты прилетают в виде строк. Подумать, как передовать с фронта или где-то выцеплять с бэка данные из фильтра по дате. Может, использовать IntervalDto как-то
    @ApiResponse(
            responseCode = "200",
            description = "Элемент успешно добавлен в корзину")
    public void add(Principal principal, @PathVariable String uuid, @PathVariable Long instrumentId
            , @PathVariable String startDate, @PathVariable String endDate) {
        cartService.addToCart(getCurrentCartUuid(principal, uuid), instrumentId, startDate, endDate);
    }

    @Operation(summary = "Удаление из корзины")
    @GetMapping("/{uuid}/remove/{instrumentId}")
    @ApiResponse(
            responseCode = "200",
            description = "Элемент успешно удален из корзины")
    public void remove(Principal principal, @PathVariable String uuid, @PathVariable Long instrumentId) {
        cartService.removeItemFromCart(getCurrentCartUuid(principal, uuid), instrumentId);
    }

    @Operation(summary = "Чистка корзины")
    @GetMapping("/{uuid}/clear")
    @ApiResponse(
            responseCode = "200",
            description = "Корзина очищена")
    public void clear(Principal principal, @PathVariable String uuid) {
        cartService.clearCart(getCurrentCartUuid(principal, uuid));
    }

    @Operation(summary = "Слияние корзины незарегестрированного пользователя с его корзиной после регистрации/аутентификации")
    @GetMapping("/{uuid}/merge")
    @ApiResponse(
            responseCode = "200",
            description = "Слияние корзин произведено успешно")
    public void merge(Principal principal, @PathVariable String uuid) {
        cartService.merge(
                getCurrentCartUuid(principal, null),
                getCurrentCartUuid(null, uuid)
        );
    }

    @Operation(summary = "Получение OrderDto из Cart")
    @GetMapping("/{uuid}/order")
    @ApiResponse(
            responseCode = "200",
            description = "")
    public OrderDto getOrderDto(Principal principal, @PathVariable String uuid) {
        String cartUuid = getCurrentCartUuid(principal, uuid);
        return cartService.convertCartToOrder(principal, cartUuid);
    }

    private String getCurrentCartUuid(Principal principal, String uuid) {
        if (principal != null) {
            return cartService.getCartUuidFromSuffix(principal.getName());
        }
        return cartService.getCartUuidFromSuffix(uuid);
    }

}
