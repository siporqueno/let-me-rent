package ru.letmerent.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Модель позиции в заказе")
@ToString
public class OrderItemDto {

    @Schema(description = "Идентификатор позиции в заказе", example = "1234567")
    Long id;

    @Schema(description = "Дата начала аренды", example = "2015-11-21T12:48:00.973")
    LocalDateTime dateStart;

    @Schema(description = "Дата завершения аренды", example = "2015-11-21T12:48:00.973")
    LocalDateTime dateFinish;

    @Schema(description = "Модель заказа")
    OrderDto order;

    @Schema(description = "Модель инструмента")
    InstrumentDto instrument;
}
