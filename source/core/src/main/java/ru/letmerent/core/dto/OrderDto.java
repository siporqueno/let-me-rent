package ru.letmerent.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Модель заказа")
@ToString
public class OrderDto {

    @Schema(description = "Идентификатор заказа", example = "12345678")
    Long id;

    @Schema(description = "Модель пользователя арендатора")
    UserDto renter;

    @Schema(description = "Дата начала аренды", example = "2015-11-21T12:48:00.973")
    LocalDateTime dateStart;

    @Schema(description = "Дата заверщения аренды", example = "2015-11-21T12:48:00.973")
    LocalDateTime dateFinish;

    @Schema(description = "Список позиций в заказе")
    List<OrderItemDto> orderItems;
}