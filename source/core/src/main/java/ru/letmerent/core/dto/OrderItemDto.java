package ru.letmerent.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Модель позиции в заказе")
@ToString
public class OrderItemDto {

    @Schema(description = "Идентификатор позиции в заказе", example = "1234567")
    Long id;

    @Schema(description = "Дата начала аренды", example = "2015-11-21T12:48:00.973")
    LocalDateTime startDate;

    @Schema(description = "Дата завершения аренды", example = "2015-11-21T12:48:00.973")
    LocalDateTime endDate;

//    @Schema(description = "Модель заказа")
//    OrderDto order;

    @Schema(description = "Модель инструмента")
    InstrumentDto instrument;

    @Schema(description = "Стоимость аренды за период от даты начала до даты окончания включительно")
    BigDecimal rentPrice;

    @Schema(description = "Длительность аренды в днях за период от даты начала до даты окончания включительно")
    Long rentLength;

    public OrderItemDto(InstrumentDto instrument, LocalDateTime startDate, LocalDateTime endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.instrument = instrument;
        this.rentLength = Duration.between(startDate,endDate).toDays() +1L; //это если считаем все дни включительно
        this.rentPrice = instrument.getFee().multiply(new BigDecimal(rentLength));
    }

}
